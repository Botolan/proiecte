.386
.model flat, stdcall

includelib msvcrt.lib
extern printf:proc
extern scanf:proc
extern fscanf:proc
extern fopen:proc
extern fclose:proc
extern exit:proc
extern strcmp:proc
extern time:proc
extern strcpy:proc
extern fprintf:proc
extern memcpy:proc


public start


.data
a dd 4f27h
dimensiune_inregistrare dd 188
afisare_citire_un_numar db "%d", 10, 0
un_string db "%s" , 0
hexa db "%X" ,10, 0 
nume_fisier db "useri.txt", 0
format_citire_useri db "%s %s %s %s %s",10, 0
format_add_useri db "%s %s %s %s %s", 0
format_afisare_useri db "%-20s / ********* / %-20s / %-20s / %-20s / %-20s",10, 0
format_citire_fisier db "r", 0
format_scriere_fisier db "w", 0
pointer_fisier dd 0
numar_useri dd 0
variabila_aux dd 0
index_gasit dd 0
index_logat dd 0

adresa_aux dd 0

mesaj_steregere_utilizator_logat db "Nu poti sterge utilizatorul logat", 10, 0
mesaj_steregere_nereusita db "Utilizatorul nu exista", 10, 0
mesaj_stergere_reusita db "User sters cu succes", 10, 0
mesaj_adaugare_reusita db "User adaugat cu succes", 10, 0
mesaj_user_existent db "Userul exista deja", 10, 0
mesaj_logare_reusita db "Logare reusita: %s %s", 10, 0
mesaj_logare_esuata db "Logarea a esuat", 10 ,0
afisare_instructiune_necunoscuta db "Instructiune necunoscuta", 10, 0
mesaj_user_negasit db "Userul nu a fost gasit", 10, 0
prompt_asteptare_instructiune db 13,">", 0
string_delimitator db "-------------------------------------------------------------------------------", 10, 0

string_gol_date_logare db 40 dup(0)
string_gol_date_instructiune db 10 dup(0)
username_stergere db 40 dup(0)

prompt_user db "username:", 0
prompt_password db "password:", 0

adaugare_username db "username:", 0
adaugare_parola db "parola:", 0
adaugare_nume db "nume:", 0
adaugare_prenume db "prenume:", 0
adaugare_email db "email:", 0

sir_caractere db "abcdefghijklmnopqrstuvwxyz123456789", 0 ;35
cheie_criptare dd 4ae935ach
parola_cripta db 20 dup(0) 
parola_decriptata db 20 dup(0)

iesire db "exit", 0
adaugare db "add", 0
stergere db "del", 0
cautare db "find", 0
afisare db "list", 0


timp dd 0



user struc
	username db 40 dup(0h)
	parola db 21 dup(0h)
	nume db 40 dup (0h)
	prenume db 40 dup(0h)
	id db 7 dup(0h)
	email db 40 dup(0h)
user ends

dd 0;
dw 0;

useri user 20 dup(<>)

instructiune dd 10 dup(0)
id_user_logare dd 40 dup(0)
parola_user_logare dd 40 dup(0)
criteriu_cautare dd 40 dup(0)


.code

citire proc
	;;;luam timpul pentru a genera id-ul random
	push offset timp
	call time
	add esp, 4
	;;;deschidem fisierul
	push offset format_citire_fisier
	push offset nume_fisier
	call fopen
	add esp, 8
	;;;memoram pointerul catre fisier
	mov pointer_fisier, eax
	;;;citim numarul de useri
	push offset numar_useri
	push offset afisare_citire_un_numar
	push pointer_fisier
	call fscanf
	add esp, 12

	mov ecx, numar_useri 	;;;memoram in ecx numarul de useri
	lea esi, useri ;;;memoram in esi adresa primului user
	
	
	bucla_citire_useri:
		pusha
		
		add esi, 148 
		push esi ;email
		sub esi, 47
		push esi ;prenume
		sub esi, 40
		push esi ;nume
		sub esi, 21
		push esi ;parola
		sub esi, 40
		push esi ;username
		push offset format_citire_useri
		push pointer_fisier
		call fscanf
		add esp, 28
		
		;0 terminal la siruri
		
		mov eax, 0
		add esi, 39
		mov [esi], al
		add esi, 21
		mov [esi], al
		add esi, 40
		mov [esi], al
		add esi, 40
		mov [esi], al
		add esi, 7
		mov [esi], al
		add esi, 40
		mov [esi], al
		
		sub esi, 46
		
		mov variabila_aux, 6
		;;generam id-ul random
		generare_id:
			mov eax, [timp]
			mov ah, 0
			mov cl, 23h
			div cl
			mov al, ah
			mov ebx, offset sir_caractere
			xlat
			
			mov [esi], al
			
			
			mov eax,[timp]
			mul a
			push dx
			push ax
			pop eax
			add eax,0b383f65dh
			mov [timp],eax

			inc esi			
			
			dec variabila_aux
			cmp variabila_aux, 0
			jne generare_id
		
		
		sub esi, 147
		
		popa
		
		add esi, 188
		sub ecx, 1
		cmp ecx, 0
	jne bucla_citire_useri

	
	push pointer_fisier
	call fclose
	add esp, 4
	ret
citire endp

criptare_parola proc
	
	push ebp
	mov ebp, esp ; pregatim stack frame-ul
	sub esp, 4 ; facem loc pe stiva pentru o variabila
	
	mov edx, [ebp+8];citim argumentul de pe stiva
	mov ecx, 0	
	bucla_criptare:
		pusha
		
		mov eax, [edx]
		cmp eax, 0
		je oprire
		xor eax, cheie_criptare
		mov [edx], eax
		
		
		popa		
		add edx, 4
		add ecx, 4

		cmp ecx, 18
		jge iesire_bucla
		
		jmp bucla_criptare
		
		oprire:
			popa
		iesire_bucla:
			mov esp, ebp
			pop ebp
			ret 4
criptare_parola endp



afisare_user proc
	push ebp
	mov ebp, esp ; pregatim stack frame-ul
	sub esp, 4 ; facem loc pe stiva pentru o variabila
	
	mov edx, [ebp+8];citim argumentul de pe stiva
	
	;;;afisare user la adresa din edx
	pusha
	add edx, 148
	push edx ;email
	sub edx, 7
	push edx ;id1	
	sub edx, 40
	push edx ;prenume
	sub edx, 40
	push edx ;nume
	sub edx, 61
	push edx ;username
	push offset format_afisare_useri
	call printf
	add esp, 24	
	popa
	;;;afisare user la adresa din edx
	
	;;;incheiere afisare
	mov esp, ebp
	pop ebp
	ret 4
	;;;incheiere afisare
afisare_user endp

afisare_useri proc
	
	mov ecx, numar_useri 	;;;memoram in ecx numarul de useri
	lea esi, useri ;;;memoram in esi adresa primului user
	pusha
	push numar_useri
	push offset afisare_citire_un_numar
	call printf
	add esp, 8
	popa
	
	pusha 
	push offset string_delimitator
	push offset un_string
	call printf
	add esp, 8
	popa
	
	bucla_afisare_useri:
		push esi ;;;punem argumentul de stiva
		call afisare_user ;;;apelam procedura de afisare a userului de pe pozitia esi
		add esi, 188 ;;; mergem la urmatorul user
	loop bucla_afisare_useri
	
	pusha 
	push offset string_delimitator
	push offset un_string
	call printf
	add esp, 8
	popa
	
	ret
	
afisare_useri endp

logare proc
	bucla_logare:
		push offset prompt_user
		push offset un_string
		call printf
		add esp, 8
		;;;resetam ce a fost introdus mai inainte
		mov ecx, 160
		lea esi, id_user_logare
		mov al, 0
		bucla0:
			mov [esi], al
			inc esi
		loop bucla0
		
		mov ecx, 160
		lea esi, parola_user_logare
		bucla1:
			mov [esi], al
			inc esi
		loop bucla1
			
		
		
		;;;citim usernameul
		push offset id_user_logare
		push offset un_string
		call scanf
		add esp, 8
		;;;veriicam daca este comanda exit
		push offset id_user_logare
		push offset iesire
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je iesire_program

		push offset prompt_password
		push offset un_string
		call printf
		add esp, 8
		;;;citim password
		push offset parola_user_logare
		push offset un_string
		call scanf
		add esp, 8
		
		mov ecx, numar_useri
		lea esi, useri
		;;;cautam userul dupa usernameul si parola introdusa
		bucla_verificare_date:
			pusha
			;;;verificam usernameul
			push offset id_user_logare
			push esi
			call strcmp
			add esp, 8
			;;;daca se potriveste, verificam parola
			cmp eax, 0
			je verificare_parola
			jmp continuare;;;daca nu se potriveste sare la iteratia urmatoare
			
			verificare_parola:
				
				add esi, 40
				push offset parola_user_logare
				call criptare_parola
				
				push offset parola_user_logare
				push esi
				call strcmp
				add esp, 8
				
				cmp eax, 0 
				je logare_reusita
				sub esi, 40
				
		continuare:
			popa
			add esi, 188
		loop bucla_verificare_date

		logare_nereusita:
			push offset mesaj_logare_esuata
			push offset un_string
			call printf
			add esp, 8
			jmp bucla_logare

		logare_reusita:
			popa
			mov edx, numar_useri
			sub edx, ecx
			mov index_logat, edx
			pusha
			add esi, 61
			push esi
			add esi, 40
			push esi
			push offset mesaj_logare_reusita
			call printf
			add esp, 12
			popa
			ret
		iesire_program:
			call exit
logare endp


citire_instructiuni proc
	bucla:
	
		push offset prompt_asteptare_instructiune
		push offset un_string
		call printf
		add esp, 8
		;;;se citeste instructiunea
		push offset instructiune
		push offset un_string
		call scanf
		add esp, 8
		
		;;;verificare instructiune de exit
		push offset instructiune
		push offset iesire
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je iesire_bucla
		;;;verificare instructiune de afisare
		push offset instructiune
		push offset afisare
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je afisare_useri1
		;;;verificare instructiune de cautare
		push offset instructiune
		push offset cautare
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je cautare_user
		;;;verificare instructiune de adaugare
		push offset instructiune
		push offset adaugare
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je adaugare_user
		
		;;;verificare instructiune de stergere
		push offset instructiune
		push offset stergere
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je stergere_user
		
		;;;cand se ajunge aici, instructiunea este necunoscuta
		jmp instructiune_necunoscuta
		
		afisare_useri1:
			call afisare_useri
			jmp iteratie_noua
		cautare_user:
			call citire_criteriu
			jmp iteratie_noua
		adaugare_user:
			call adaugare_user_nou
			jmp iteratie_noua
		stergere_user:
			call stergere_utilizator
			jmp iteratie_noua
		instructiune_necunoscuta:
			push offset afisare_instructiune_necunoscuta
			push offset un_string
			call printf
			add esp, 8
		iteratie_noua:
			jmp bucla
		iesire_bucla:
			call scriere_fisier
			ret
citire_instructiuni endp

stergere_utilizator proc
	pusha
	push offset username_stergere
	push offset un_string
	call scanf
	add esp, 8
	popa
	
	push offset username_stergere
	call gasire_user
	
	cmp index_gasit, -1
	je steregere_user_inexistent
	
	mov eax, index_logat
	cmp index_gasit, eax
	je stergere_utilizator_logat
	
	sterg:
		pusha
		lea esi, useri
		mov eax, index_gasit
		mul dimensiune_inregistrare
		add esi, eax
		
		lea ebx, useri
		mov eax, numar_useri
		sub eax, 1
		mul dimensiune_inregistrare
		add ebx, eax
		
		push 188
		push ebx
		push esi
		call memcpy
		add esp, 12
		
		dec numar_useri
		popa
		pusha
		push offset mesaj_stergere_reusita
		push offset un_string
		call printf
		add esp, 8
		popa
		jmp iesire_stergere
	stergere_utilizator_logat:
		pusha
		push offset mesaj_steregere_utilizator_logat
		push offset un_string
		call printf
		add esp, 8
		popa
		jmp iesire_stergere
	steregere_user_inexistent:
		pusha
		push offset mesaj_steregere_nereusita
		push offset un_string
		call printf
		add esp, 8
		popa
	iesire_stergere:
		ret
	


stergere_utilizator endp

adaugare_user_nou proc

		
		pusha
		
		mov eax, numar_useri
		mul dimensiune_inregistrare
		lea esi, useri
		add esi, eax
	
	
		push offset adaugare_username
		push offset un_string
		call printf
		add esp, 8
		
		push esi ;username
		push offset un_string
		call scanf
		add esp, 8
		
		push offset adaugare_parola
		push offset un_string
		call printf
		add esp, 8
		
		add esi, 40
		push esi;parola
		push offset un_string
		call scanf
		add esp, 8
		
		push offset adaugare_nume
		push offset un_string
		call printf
		add esp, 8
		
		add esi, 21
		push esi ;nume
		push offset un_string
		call scanf
		add esp, 8
		
		push offset adaugare_prenume
		push offset un_string
		call printf
		add esp, 8
		
		add esi, 40
		push esi ;prenume
		push offset un_string
		call scanf
		add esp, 8
		
		push offset adaugare_email
		push offset un_string
		call printf
		add esp, 8
		
		add esi, 47;email
		push esi
		push offset un_string
		call scanf
		add esp, 8
		
		sub esi, 148
		mov adresa_aux, esi
		
		popa
		
		push adresa_aux
		call gasire_user
		cmp index_gasit, -1
		jne user_existent
		
		
		mov esi, adresa_aux
		;0 terminal la siruri
		add esi, 40
		push esi
		call criptare_parola
		sub esi, 40
		
		mov eax, 0
		add esi, 39
		mov [esi], al
		add esi, 21
		mov [esi], al
		add esi, 40
		mov [esi], al
		add esi, 40
		mov [esi], al
		add esi, 7
		mov [esi], al
		add esi, 40
		mov [esi], al
		
		sub esi, 46
		pusha
		mov variabila_aux, 6
		;;generam id-ul random
		generare_id:
			mov eax, [timp]
			mov ah, 0
			mov cl, 23h
			div cl
			mov al, ah
			mov ebx, offset sir_caractere
			xlat
			
			mov [esi], al
			
			
			mov eax,[timp]
			mul a
			push dx
			push ax
			pop eax
			add eax,0b383f65dh
			mov [timp],eax

			inc esi			
			
			dec variabila_aux
			cmp variabila_aux, 0
			jne generare_id
		
		
			sub esi, 147
		
			popa
		

		adaugare_reusita:
			inc numar_useri
			pusha
			push offset mesaj_adaugare_reusita
			call printf
			add esp, 4
			popa
			jmp iesire_adaugare
		
		user_existent:
			mov eax, numar_useri
			mul dimensiune_inregistrare
			lea esi, useri
			add esi, eax
			bucla_stergere:
				mov [esi], al
				inc esi
			loop bucla_stergere
				
			pusha
			push offset mesaj_user_existent
			call printf
			add esp, 4
			popa
		iesire_adaugare:
			ret
adaugare_user_nou endp

gasire_user proc
	push ebp
	mov ebp, esp ; pregatim stack frame-ul
	sub esp, 4 ; facem loc pe stiva pentru o variabila
	
	mov edx, [ebp+8];citim argumentul de pe stiva
	
	lea esi, useri
	mov ecx, numar_useri
	;;;variabila folosita cand vrem sa vedem daca un anumit utilizator exista
	mov index_gasit, -1
	bucla_cautare:
		pusha
		;;;cautare dupa username
		push edx
		push esi
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je gasit
		
		popa
		add esi, 61
		pusha
		;;cautare dupa nume
		push edx
		push esi
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je gasit
		
		popa
		add esi, 40
		pusha
		;;cautare dupa prenume
		push edx
		push esi
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je gasit
		
		popa
		add esi, 47
		pusha
		;;;cautare dupa email
		push edx
		push esi
		call strcmp
		add esp, 8
		
		cmp eax, 0
		je gasit
		popa
		
		add esi, 40
	loop bucla_cautare

	jmp incheiere
	
	gasit:
		;;;setam variabila index_gasit la pozitia pe care a fost gasit userul cautat
		popa
		mov ebx, numar_useri
		sub ebx, ecx
		mov index_gasit, ebx

	;;;incheiere afisare
	incheiere:
		mov esp, ebp
		pop ebp
		ret 4
	;;;incheiere afisare

gasire_user endp


citire_criteriu proc
	push offset criteriu_cautare
	push offset un_string
	call scanf
	add esp, 8
	
	push offset criteriu_cautare
	call gasire_user
	;;;daca index_gasit este -1, userul nu a fost gasit
	cmp index_gasit, -1
	je negasit
	
	gasit:
		pusha
		lea esi, useri
		mov eax, index_gasit
		mov variabila_aux, 188
		mul variabila_aux
		add esi, eax
		push esi
		call afisare_user
		popa
		ret
	negasit:
		pusha
		push offset mesaj_user_negasit
		push offset un_string
		call printf
		add esp, 8
		popa
		ret
citire_criteriu endp
	
scriere_fisier proc

	push offset format_scriere_fisier
	push offset nume_fisier
	call fopen
	add esp, 8
	
	mov pointer_fisier, eax
	
	lea esi, useri
	pusha
	push numar_useri
	push offset afisare_citire_un_numar
	push pointer_fisier
	call fprintf
	add esp, 12
	popa
	
	mov ecx, numar_useri

	bucla_scriere:
		pusha
		add esi, 148 
		push esi ;email
		sub esi, 47
		push esi ;prenume
		sub esi, 40
		push esi ;nume
		sub esi, 21
		push esi ;parola
		sub esi, 40
		push esi ;username
		push offset format_citire_useri
		push pointer_fisier
		call fprintf
		add esp, 28
		popa
		add esi,188
	loop bucla_scriere
	
	push pointer_fisier
	call fclose
	add esp, 4
	
	ret

scriere_fisier endp

start:
	call citire
	call logare
	call citire_instructiuni
	push 0
	call exit
end start;
