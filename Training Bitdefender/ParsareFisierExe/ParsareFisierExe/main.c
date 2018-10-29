#include <stdio.h>
#include <windows.h>

typedef struct {
	HANDLE hFile;
	HANDLE hMapping;
	BYTE *data;
	DWORD fileSize;
}MAPPED_FILE;

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

int parsePeFile(MAPPED_FILE *mf) {
	IMAGE_DOS_HEADER *pDos = NULL;
	IMAGE_NT_HEADERS *pNt = NULL;
	IMAGE_SECTION_HEADER *pSec = NULL;
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
	printf("nrSections = %d\n", nrSections);
	printf("characteristics = 0x%x\n", pNt->FileHeader.Characteristics);
	if (pNt->FileHeader.Characteristics & IMAGE_FILE_DLL != 0) {
		printf("E DLL.\n");
	}
	else {
		printf("Nu e DLL.\n");
	}
	printf("ImageBase = 0x%x\n", pNt->OptionalHeader.ImageBase);
	printf("EntryPoint = 0x%x\n", pNt->OptionalHeader.AddressOfEntryPoint);

	pSec = (IMAGE_SECTION_HEADER*)(mf->data + pDos->e_lfanew + sizeof(IMAGE_NT_HEADERS));
	if (pDos->e_lfanew + sizeof(IMAGE_NT_HEADERS) + (sizeof(IMAGE_SECTION_HEADER) * nrSections) > mf->fileSize) {
		printf("Fisier prea mic.\n");
		return -5;
	}

	for (BYTE bIndex = 0; bIndex < nrSections; bIndex++) {
		printf("Informatii despre sectiunea numarul %d\n", bIndex + 1);
		printf("	Numele sectiuni = %s\n", pSec->Name);
		printf("	VirtualSize = %x\n", pSec->Misc.VirtualSize);
		printf("	VirtualAddress = %x\n", pSec->VirtualAddress);
		printf("	SizeOfRawData = %x\n", pSec->SizeOfRawData);
		printf("	PointerToRawData = %x\n", pSec->PointerToRawData);
		printf("	PointerToRelocation = %x\n", pSec->PointerToRelocations);
		printf("	PointerToLinenumbers = %x\n", pSec->PointerToLinenumbers);
		printf("	NumberOfRelocations = %x\n", pSec->NumberOfRelocations);
		printf("	NumberOfLinenumbers = %x\n", pSec->NumberOfLinenumbers);
		printf("	Caracateristici = %x\n", pSec->Characteristics);
		pSec++;
	}

	//mutam pSec la prima sectiune
	pSec -= nrSections;

	return 0;
}

int main()
{
	MAPPED_FILE mf;
	if (memoryMap("test1.exe", &mf) == 0) {
		if (parsePeFile(&mf) != 0) {
			printf("Eroare la parsare!\n");
		}
	}
	else {
		printf("Eroare la mapare: %d.\n", GetLastError());
	}
	memoryUnmap(&mf);
	return 0;
}
