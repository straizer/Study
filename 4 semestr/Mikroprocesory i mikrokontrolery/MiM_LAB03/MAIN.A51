EXTRN CODE (random_init, random_get)

PROGRAM SEGMENT CODE
	
LightUpTime					EQU 3					; initial diode light up time (3 * 50ms)
GuessesUntilLightUpIncrease EQU 3					; number of guesses until diode light up time is increased

CSEG AT 0h
	JMP main

RSEG PROGRAM
main:
		MOV		R2, #LightUpTime
		MOV		R3, #GuessesUntilLightUpIncrease
		ACALL	random_init							; initialize random generator
		ACALL	wait_to_press_any_to_start			; wait for user to press any button to start
	main__loop:
			ACALL	wait_3000ms						; wait 3s before diode is light up
			ACALL 	random_get						; random number 0-3 is in A
			ACALL	light_up_diode					; light up diode represented by random number
			ACALL	wait_for_press					; wait for button press; carry bit set - correct button pressed, carry bit reset - incorrect button pressed
			ACALL 	show_diode_sequence				; sequence displayed when button was pressed			
			ACALL	wait_for_release				; wait for release all buttons
			JMP		main__loop

wait_to_press_any_to_start:
		MOV		A, P3
		CPL		A
		JZ		wait_to_press_any_to_start
		ACALL	wait_for_release
		RET

; A, R0, P2
light_up_diode:
		ADD		A, #1
		MOV		R0, A
		MOV		A, #1
	light_up_diode__selector_loop:
			DJNZ	R0, light_up_diode__diode_not_selected
			JMP		light_up_diode__diode_selected
		light_up_diode__diode_not_selected:	
			RL		A
			JMP		light_up_diode__selector_loop
	light_up_diode__diode_selected:	
		CPL		A
		MOV		P2, A
		RET

; A, C, R0-3
wait_for_press:
			MOV		A, R2
			MOV		R1, A
			wait_for_press__R1_x_50ms_loop:
				MOV		R0, #25
				wait_for_press__50ms_loop:
					ACALL	wait_2ms
					MOV		A, P3
					CPL		A
					JNZ		wait_for_press__button_pressed_or_timeout
					DJNZ	R0, wait_for_press__50ms_loop
				DJNZ	R1, wait_for_press__R1_x_50ms_loop
			DJNZ	R3, wait_for_press__button_pressed_or_timeout
			INC		R2
			MOV		R3, #GuessesUntilLightUpIncrease
		wait_for_press__button_pressed_or_timeout:	
			SETB	C
			CPL		A
			XRL		A, P2
			JZ		wait_for_press__correct_button
			CLR		C
		wait_for_press__correct_button:
			RET

; A, C, R0, R6-7, P2	
show_diode_sequence:
			JC		correct_button
			ACALL 	show_incorrect_diode_sequence	; sequence displayed when incorrect button was pressed
			RET
		correct_button:	
			ACALL	show_correct_diode_sequence		; sequence displayed when correct button was pressed	
			RET

; A, R0, R6-7, P2
show_incorrect_diode_sequence:
		MOV		R0, #33
		MOV		A, #0FFh
	show_incorrect_diode_sequence__loop:
		ACALL	wait_100ms
		MOV		P2, A
		CPL 	A
		DJNZ	R0, show_incorrect_diode_sequence__loop
		RET
	
; A, C, R0, R6-7, P2	
show_correct_diode_sequence:
		MOV		R0, #8
		MOV		A, #0FFh
	show_correct_diode_sequence__loop1:
			ACALL	wait_100ms
			MOV		P2, A
			CLR		C
			RLC		A
			DJNZ	R0, show_correct_diode_sequence__loop1				
		MOV		R0, #8
		MOV		A, #0h
	show_correct_diode_sequence__loop2:
			ACALL	wait_100ms
			MOV		P2, A
			SETB	C
			RLC		A
			DJNZ	R0, show_correct_diode_sequence__loop2			
		MOV		R0, #8
		MOV		A, #0FFh
	show_correct_diode_sequence__loop3:
			ACALL	wait_100ms
			MOV		P2, A
			CLR		C
			RRC		A
			DJNZ	R0, show_correct_diode_sequence__loop3				
		MOV		R0, #8
		MOV		A, #0h
	show_correct_diode_sequence__loop4:
			ACALL	wait_100ms
			MOV		P2, A
			SETB	C
			RRC		A
			DJNZ	R0, show_correct_diode_sequence__loop4	
		
		ACALL	wait_100ms
		MOV		P2, #0FFh
		RET

; A
wait_for_release:		
		MOV		A, P3
		CPL		A
		JNZ		wait_for_release
		RET

; lasts almost exactly 2ms on 11.0592 MHz target
wait_2ms:
		MOV		TMOD, #01h
		MOV		TH0, #0F8h
		MOV		TL0, #0DBh
		SETB	TR0
		JNB		TF0, $
		CLR		TF0
		CLR		TR0
		RET
	
; lasts exactly 100ms on 11.0592 MHz target	
wait_100ms:
		MOV		TMOD, #01h
		MOV		TH0, #0h
		MOV		TL0, #0h
		SETB	TR0
		JNB		TF0, $
		CLR		TF0
		CLR		TR0
		MOV		TH0, #098h
		MOV		TL0, #017h
		SETB	TR0
		JNB		TF0, $
		CLR		TR0
		CLR		TF0
		RET

; R0 - lasts exactly 3s on 11.0592 MHz target		
wait_3000ms:
		MOV		TMOD, #01h
		MOV		TH0, #0h
		MOV		TL0, #0h
		SETB	TR0
		MOV		R0, #42
	wait_3000ms__loop:
			JNB		TF0, $
			CLR		TF0
			DJNZ	R0, wait_3000ms__loop
		CLR		TR0
		MOV		TH0, #0D0h
		MOV		TL0, #019h
		SETB	TR0
		JNB		TF0, $
		CLR		TF0
		CLR		TR0
		RET
	
END