PROGRAM SEGMENT CODE
	
CSEG AT 00h
	JMP main
	
CSEG AT 0Bh
	JMP t0_handle
	
CSEG AT 1Bh
	JMP t1_handle
	
RSEG PROGRAM
main:
		MOV		TMOD, #11h
		SETB	TR0		
		SETB	EA
		SETB	ET0
		SETB	ET1
		MOV		R0, #14
		JMP		$

t0_handle:
		DJNZ	R0, t0_handle__return
		CLR		TR0
		MOV		TH1, #0F0h
		MOV		TL1, #016h	
		SETB	TR1
	t0_handle__return:
		RETI
		
t1_handle:		
		CLR		TR1
		MOV		TH0, #000h
		MOV		TL0, #000h	
		SETB	TR0
		MOV		R0, #14
		NOP
		INC		A
		CPL		A
		MOV		P2, A
		CPL		A
		RETI

END