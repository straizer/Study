MAIN SEGMENT CODE
	
Destination	DATA 20h
DataAmount 	EQU 5
	
CSEG AT 0h
	JMP start

RSEG MAIN
	start:
		MOV		DPTR, #hardcoded_data
		MOV 	R0, #Destination
		MOV 	R1, #DataAmount
	loop:
		MOV		A, #0h
		MOVC 	A, @A+DPTR
		MOV 	@R0, A
		INC 	DPTR
		INC 	R0
		DJNZ 	R1, loop
		
		SJMP 	$

	hardcoded_data:
		DB		11, 21, 4, 18, 8
END