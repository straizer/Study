;	Assembly code is separated into segments that can be saved only into specified addresses. There are 5 types of segments:
;	BIT		- bit adresses in range  	 20h -   2Fh
;	CODE	- code adresses in range   0000h - FFFFh
;	DATA	- memory adresses in range   00h -   7Fh and SFR (special registers in addresses 80h - FFh)
;	IDATA	- memory adresses in range   00h -   FFh (internal RAM)
;	XDATA	- memory adresses in range 0000h - FFFFh (external RAM)

;	Addressing modes:
;	Immediate	- #<value>
;	Register	- <register>
;	Direct		- <address>
;	Indirect	- @<register> (address of DATA is in register (MOV)) OR @DPTR (address of XDATA is in DPTR register (MOVX))
;	Indexed		- @A+<register> (used to access ROM (MOVC))

Constant	EQU 1000				; <name> EQU <expression>		-	define constant, like #define in C; must be evaluated at compile time
DataLow		DATA 20h				; <name> SET <expression>		-	like EQU, but value can be changed
DataHigh	DATA 21h				; <name> (I|X)?DATA <address>	-	create 'C pointer' that points to specified memory address;
ResultLow	DATA 30h				;									20h is first address allowed for variables;
ResultHigh	DATA 31h				;									there is also IDATA and XDATA for their respective segments
					
CSEG AT 0h							; _SEG AT <address>				-	used as label for compiler to indicate what type of data and where in memory it will be added;
									;									possible options for respective segments: BSEG, CSEG, DSEG, ISEG, XSEG																					
	JMP start						; JMP <label>					-	unconditionally jump to label

CSEG AT 100h
	
	start:
		MOV		A, DataLow			; MOV <destination>, <source>	-	transfer data between RAM or SFR locations; upper 128 bytes of data RAM can be addressed indirectly,
									;									and SFR space can be addressed only directly	
		SUBB	A, #LOW(Constant)	; ADD A, <value>				-	add value to accumulator; if overflows out of bit 7, CY/PSW.7 (carry bit) is set
		MOV		ResultLow, A		; LOW(<value>)					-	get low byte of 2-byte variable; analogically HIGH(<value>)
		MOV		A, DataHigh
		SUBB	A, #HIGH(Constant)	; ADDC A, <value> 				-	same as ADD, but also adds CY/PSW.7 (carry bit)
		MOV 	ResultHigh, A

END									; END							- obligatory at end of program, anything beyond END is ommited