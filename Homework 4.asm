TITLE Find the squared version of a number (Homework 4.asm)

; This program adds two 32-bit integers

INCLUDE Irvine32.inc

.data
pmt		BYTE	"Enter a number: ",0
lbl		BYTE	" squared is ",0


.code
main	PROC
; get the first number
		mov		edx,offset pmt		; prepare to display prompt
		call	WriteString			; display prompt (addr in EDX)
		call	ReadInt				; get the first number (in EAX)

; call the subroutine
		call	WriteDec
		mov		ebx, eax			; keep the original value of the user inserted number to be preserved
		call	sqr

; display the result
		mov		edx,offset lbl		; identify the result
		call	WriteString
		call	WriteDec			; display result (value in EAX)
		call	CrLf
		exit
main	ENDP

; subroutine sqr
; input: 
; output: 
sqr	PROC
		mov		esi, 1				; intialize counter
		mov		ecx, 1				; initialize x value
		mov		eax, 1				; initializa y value
	olp:
		add		ecx, 2				; add 2 to x value
		add		eax, ecx			; add x to y value
		inc		esi
		cmp		esi, ebx			; stop counter at user entered value
		jl		olp
		ret							; return from the subroutine
		
sqr	ENDP
END	main
