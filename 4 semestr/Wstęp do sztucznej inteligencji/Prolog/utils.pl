get_input(Prompt, Input) :-
	write(Prompt),
	read(Input).
	
with_open(FileName, LineConsumer) :-
	seeing(OldFileName),
	see(FileName),
	consume_lines(LineConsumer),
	seen,
	see(OldFileName).
	
consume_lines(LineConsumer) :-
	repeat,
	read(Line),
	\+ 
	(
		Line == end_of_file
	->	fail
	;	call(LineConsumer, Line)
	).

read_all :-
	get_input('Enter file name: ', FileName),
	with_open(FileName, line_consumer),
	write('Done!').
	
line_consumer(Line) :-
	assertz(imie(Line)).
