RANDOM SEGMENT CODE
	
J			EQU 7
K			EQU 10
M			EQU 4		
BufferStart	EQU 20h
	
BufferEnd	EQU BufferStart+K
BufferIdx	EQU BufferEnd+1
J_COMP		EQU K-J	
Buffer		DATA BufferStart
		
PUBLIC random_init, random_get

RSEG RANDOM
; DPTR, A, R0-1
random_init:
		MOV		DPTR, #seed
		MOV 	R0, #Buffer
		MOV 	R1, #K
	random_init__loop:
			CLR		A
			MOVC 	A, @A+DPTR
			MOV 	@R0, A
			INC 	DPTR
			INC 	R0
			DJNZ 	R1, random_init__loop		
		MOV 	BufferIdx, #Buffer	
		RET

; A, R0-1		
random_get:
		CLR		C
		MOV		R0, BufferIdx
		MOV		A, R0
		SUBB	A, #Buffer
		CJNE	A, #J, random_get__a_not_equal_j
		JMP		random_get__a_equal_or_greater_than_j
	random_get__a_not_equal_j:
		JC 		random_get__a_less_than_j		
	random_get__a_equal_or_greater_than_j:
		MOV		A, R0
		SUBB	A, #J
		MOV		R1, A
		MOV		A, @R1
		JMP 	random_get__after_compare_shift		
	random_get__a_less_than_j:
		MOV		A, R0
		ADD		A, #J_COMP
		MOV		R1, A
		MOV		A, @R1	
	random_get__after_compare_shift:	
		ADD		A, @R0
	random_get__modulo_loop:
			CJNE	A, #M, random_get__a_not_equal_m
			JMP		random_get__a_equal_or_greater_than_m
		random_get__a_not_equal_m:
			JC 		random_get__a_less_than_m		
		random_get__a_equal_or_greater_than_m:
			CLR		C
			SUBB	A, #M
			JMP 	random_get__modulo_loop		
	random_get__a_less_than_m:
		MOV		@R0, A
		INC		R0
		CJNE	R0, #BufferEnd, random_get__return
		MOV		R0, #Buffer
	random_get__return:
		MOV		BufferIdx, R0
		RET
		
seed:
	DB 4, 5, 3, 3, 3, 4, 6, 2, 6, 5			
	
END