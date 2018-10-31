#include <stdio.h>
#include <windows.h>
#include <string.h>



typedef struct {
	HANDLE hFile;
	HANDLE hMapping;
	BYTE *data;
	DWORD fileSize;
}MAPPED_FILE;

DWORD RVA2FA(DWORD RVA, IMAGE_SECTION_HEADER* pSec, WORD nrSect);
int memoryMap(const char *fileName, MAPPED_FILE *mf);
int getExportFunctions(MAPPED_FILE *mf, IMAGE_EXPORT_DIRECTORY* pExD, IMAGE_SECTION_HEADER* pSec, WORD nrSections);
int getImports(MAPPED_FILE *mf, IMAGE_IMPORT_DESCRIPTOR* pImp, IMAGE_SECTION_HEADER* pSec, WORD nrSections);
int parsePeFile(MAPPED_FILE *mf);
int loadFile(char* path);
void traverseDirectory(char* path);

int memoryMap(const char *fileName, MAPPED_FILE *mf)
{
	memset(mf, 0, sizeof(MAPPED_FILE));
	mf->hFile = INVALID_HANDLE_VALUE;
	mf->hFile = CreateFileA(
		fileName,
		GENERIC_READ,
		FILE_SHARE_READ,
		NULL,
		OPEN_EXISTING,
		FILE_ATTRIBUTE_NORMAL,
		NULL
	);

	if (mf->hFile == INVALID_HANDLE_VALUE) {
		return -1;
	}

	mf->fileSize = GetFileSize(mf->hFile, NULL);
	mf->hMapping = CreateFileMappingA(
		mf->hFile,
		NULL,
		PAGE_READONLY,
		0,
		mf->fileSize,
		NULL
	);

	if (mf->hMapping == NULL) {
		return -2;
	}

	mf->data = (BYTE*)MapViewOfFile(
		mf->hMapping,
		FILE_MAP_READ,
		0,
		0,
		mf->fileSize
	);

	return 0;
}

void memoryUnmap(MAPPED_FILE *mf) {
	if (mf->data != NULL) {
		UnmapViewOfFile(mf->data);
		mf->data = NULL;
	}

	if (mf->hMapping != NULL) {
		CloseHandle(mf->hMapping);
		mf->hMapping = NULL;
	}

	if (mf->hFile != INVALID_HANDLE_VALUE) {
		CloseHandle(mf->hFile);
		mf->hFile = INVALID_HANDLE_VALUE;
	}
}



int getExportFunctions(MAPPED_FILE *mf, IMAGE_EXPORT_DIRECTORY* pExD, IMAGE_SECTION_HEADER* pSec, WORD nrSections) {
	DWORD *pIndexOfName = NULL;
	DWORD *pIndexOfFunctionAddress = NULL;
	WORD *pIndexOfOrdinal = NULL;
	BYTE* bPointerToString = NULL;

	printf("Exporturi----------------\n");

	//adresele numelor functiilor
	pIndexOfName = (DWORD*)((DWORD)mf->data + RVA2FA(pExD->AddressOfNames, pSec, nrSections));
	//adresele functiilor
	pIndexOfFunctionAddress = (DWORD*)((DWORD)mf->data + RVA2FA(pExD->AddressOfFunctions, pSec, nrSections));
	//ordinalele functiilor
	pIndexOfOrdinal = (WORD*)((DWORD)mf->data + RVA2FA(pExD->AddressOfNameOrdinals, pSec, nrSections));


	for (DWORD dwIndex = 0; dwIndex < pExD->NumberOfFunctions; dwIndex++) {
		//facem RVA2FA pe adresa la care se afla numele
		bPointerToString = ((BYTE*)(DWORD)mf->data + RVA2FA(pIndexOfName[pIndexOfOrdinal[dwIndex]], pSec, nrSections));
		printf("Adresa functiei = 0x%x, Numele functiei = %s, Ordinalul functiei = 0x%x\n", pIndexOfFunctionAddress[dwIndex], bPointerToString, pIndexOfOrdinal[dwIndex]);
	}

	return 0;
}


int getImports(MAPPED_FILE *mf, IMAGE_IMPORT_DESCRIPTOR* pImp, IMAGE_SECTION_HEADER* pSec, WORD nrSections) {
	printf("Importuri----------------\n");
	while ((void*)pImp->Name != NULL) {
		//numele fisierului de unde se importa
		printf("%s\n", (BYTE*)(mf->data + RVA2FA(pImp->Name, pSec, nrSections)));
		//adresa la care se afla prima informatie despre ce importa
		PIMAGE_THUNK_DATA thunk = (PIMAGE_THUNK_DATA)((DWORD)mf->data + RVA2FA(pImp->FirstThunk, pSec, nrSections));
		//cat timp este diferit de NULL inseamna ca importam
		while ((void*)thunk->u1.AddressOfData != NULL) {
			//numele luat prin RVA2FA
			IMAGE_IMPORT_BY_NAME *NameAddress = (IMAGE_IMPORT_BY_NAME*)((DWORD)mf->data + RVA2FA(thunk->u1.AddressOfData, pSec, nrSections));
			printf("	%s\n", NameAddress->Name);
			thunk++;
		}
		pImp++;
	}
	return 0;
}



int parsePeFile(MAPPED_FILE *mf) {
	IMAGE_DOS_HEADER *pDos = NULL;
	IMAGE_NT_HEADERS *pNt = NULL;
	IMAGE_SECTION_HEADER *pSec = NULL;
	IMAGE_EXPORT_DIRECTORY *pExD = NULL;
	IMAGE_IMPORT_DESCRIPTOR *pImp = NULL;


	WORD nrSections;


	if (mf->fileSize < sizeof(IMAGE_DOS_HEADER)) {
		printf("Fisier prea mic.\n");
		return -1;
	}

	pDos = (IMAGE_DOS_HEADER*)mf->data;
	if (pDos->e_magic == 0x5A4D) {
		printf("Header MZ corect.\n");
	}
	else {
		printf("Header MZ incorect.\n");
		return -2;
	}

	pNt = (IMAGE_NT_HEADERS*)(mf->data + pDos->e_lfanew);
	if (pDos->e_lfanew + sizeof(IMAGE_NT_HEADERS) > mf->fileSize) {
		printf("Fisier prea mic.\n");
		return -3;
	}
	if (pNt->Signature == 0x4550) {
		printf("Header PE corect.\n");
	}
	else {
		printf("Header PE incorect.\n");
		return -4;
	}
	nrSections = pNt->FileHeader.NumberOfSections;
	if ((pNt->FileHeader.Characteristics & IMAGE_FILE_DLL) != 0) {
		printf("E DLL.\n");
	}
	else {
		printf("Nu e DLL.\n");
	}

	pSec = (IMAGE_SECTION_HEADER*)(mf->data + pDos->e_lfanew + sizeof(IMAGE_NT_HEADERS));
	if (pDos->e_lfanew + sizeof(IMAGE_NT_HEADERS) + (sizeof(IMAGE_SECTION_HEADER) * nrSections) > mf->fileSize) {
		printf("Fisier prea mic.\n");
		return -5;
	}

	for (BYTE bIndex = 0; bIndex < nrSections; bIndex++) {
		printf("Informatii despre sectiunea numarul %d\n", bIndex + 1);
		printf("	Numele sectiuni = %.8s\n", pSec->Name);
		printf("	VirtualSize = 0x%x\n", pSec->Misc.VirtualSize);
		printf("	VirtualAddress = 0x%x\n", pSec->VirtualAddress);
		printf("	SizeOfRawData = 0x%x\n", pSec->SizeOfRawData);
		printf("	PointerToRawData = 0x%x\n", pSec->PointerToRawData);
		printf("	PointerToRelocation = 0x%x\n", pSec->PointerToRelocations);
		printf("	PointerToLinenumbers = 0x%x\n", pSec->PointerToLinenumbers);
		printf("	NumberOfRelocations = 0x%x\n", pSec->NumberOfRelocations);
		printf("	NumberOfLinenumbers = 0x%x\n", pSec->NumberOfLinenumbers);
		printf("	Caracateristici = 0x%x\n", pSec->Characteristics);
		pSec++;
	}

	//mutam pSec la prima sectiune
	pSec -= nrSections;

	if ((void*)pNt->OptionalHeader.DataDirectory[0].VirtualAddress == NULL) {
		printf("Fisierul nu are exporturi!\n");
	}
	else {
		pExD = (IMAGE_EXPORT_DIRECTORY*)((DWORD)mf->data + RVA2FA(pNt->OptionalHeader.DataDirectory[0].VirtualAddress, pSec, nrSections));
		getExportFunctions(mf, pExD, pSec, nrSections);
	}

	if ((void*)pNt->OptionalHeader.DataDirectory[1].VirtualAddress == NULL) {
		printf("Fisierul nu are importuri!\n");
	}
	else {
		pImp = (IMAGE_IMPORT_DESCRIPTOR*)((DWORD)mf->data + RVA2FA(pNt->OptionalHeader.DataDirectory[1].VirtualAddress, pSec, nrSections));
		getImports(mf, pImp, pSec, nrSections);
	}

	return 0;
}


DWORD RVA2FA(DWORD RVA, IMAGE_SECTION_HEADER* pSec, WORD nrSect) {
	BYTE bIndex;

	for (bIndex = 0; bIndex < nrSect; bIndex++, pSec++) {
		if ((pSec->VirtualAddress <= RVA) && (pSec->VirtualAddress + pSec->Misc.VirtualSize > RVA)) {
			break;
		}
	}

	if (bIndex == nrSect) {
		return 0;
	}

	DWORD delta = RVA - pSec->VirtualAddress;
	return pSec->PointerToRawData + delta;
}

void traverseDirectory(char* path) {
	WIN32_FIND_DATAA data;
	HANDLE hFind;


	hFind = FindFirstFileA(path, &data);

	if (hFind == INVALID_HANDLE_VALUE) {
		printf("Eroare la deschidere fisier!\n");
		return;
	}

	while (FindNextFileA(hFind, &data)) {
		if (strcmp(data.cFileName, "..") != 0 && strcmp(data.cFileName, ".") != 0) {
			BYTE *newPath = (BYTE*)calloc(512, 1);
			memcpy(newPath, path, strlen(path) - 1);
			strcat_s(newPath, 512, data.cFileName);
			if ((data.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY) != 0) {
				strcat_s(newPath, 512, "\\*");
				traverseDirectory(newPath);
			}
			else {
				printf("Analiza pentru fisierul %s\n", newPath);
				if (loadFile(newPath) != 0) {
					printf("Fisierul nu a putut fi analizat!\n");
				}
			}
		}
	}
}

int loadFile(char* path) {
	MAPPED_FILE mf;
	if (memoryMap(path, &mf) == 0) {
		if (parsePeFile(&mf) != 0) {
			printf("Eroare la parsare!\n");
			return -1;
		}
	}
	else {
		printf("Eroare la mapare: %d.\n", GetLastError());
		return -2;
	}
	memoryUnmap(&mf);
	return 0;
}



int main()
{
	char a[] = "D:\\Facultate\\proiecte\\Training Bitdefender\\FolderTest\\*";
	traverseDirectory(a);
	return 0;
}