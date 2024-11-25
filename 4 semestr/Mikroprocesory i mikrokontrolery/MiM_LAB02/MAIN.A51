MAIN SEGMENT CODE
	
CSEG AT 0h
	JMP start
	
RSEG MAIN
	setup:
		MOV		R0, #0
		MOV		R1, #0
	start:
		CLR		C
		MOV		A, P3
		CPL 	A
		JZ		setup
		MOV		R1, A
		SUBB	A, R0
		JC		cleanup
		MOV		A, R1		
		XRL		A, R0
		XRL		A, P2
		MOV		P2, A
	cleanup:
		MOV		A, R1	
		MOV		R0, A	
		JMP		start
END