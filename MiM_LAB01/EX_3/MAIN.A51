MAIN SEGMENT CODE
	
J			EQU 7
K			EQU 10
M			EQU 4		
BufferStart	EQU 20h
	
BufferEnd	EQU BufferStart+K
J_COMP		EQU K-J	
Buffer		DATA BufferStart

CSEG AT 0h
	JMP start
	
RSEG MAIN
start:
		MOV		DPTR, #seed
		MOV 	R0, #Buffer
		MOV 	R1, #K
	start_loop:
			CLR		A
			MOVC 	A, @A+DPTR
			MOV 	@R0, A
			INC 	DPTR
			INC 	R0
			DJNZ 	R1, start_loop		
		MOV 	R0, #Buffer	
main_loop:
		CLR		C
		MOV		A, R0
		SUBB	A, #Buffer
		CJNE	A, #J, a_not_equal_j
		JMP		a_equal_or_greater_than_j
	a_not_equal_j:
		JC 		a_less_than_j		
	a_equal_or_greater_than_j:
		MOV		A, R0
		SUBB	A, #J
		MOV		R1, A
		MOV		A, @R1
		JMP 	after_compare_shift		
	a_less_than_j:
		MOV		A, R0
		ADD		A, #J_COMP
		MOV		R1, A
		MOV		A, @R1	
	after_compare_shift:	
		ADD		A, @R0
	modulo_loop:
			CJNE	A, #M, a_not_equal_m
			JMP		a_equal_or_greater_than_m
		a_not_equal_m:
			JC 		a_less_than_m		
		a_equal_or_greater_than_m:
			CLR		C
			SUBB	A, #M
			JMP 	modulo_loop		
	a_less_than_m:
		MOV		@R0, A
		INC		R0
		CJNE	R0, #BufferEnd, main_loop
		MOV		R0, #Buffer
		JMP		main_loop		
seed:
	DB 4, 5, 3, 3, 3, 4, 6, 2, 6, 5			
END