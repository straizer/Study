SET SERVEROUTPUT ON
DECLARE
    input              NUMBER(2) NOT NULL := &x;
    result             NUMBER NOT NULL    := 1;
    min_input CONSTANT NUMBER NOT NULL DEFAULT 1;
    max_input CONSTANT NUMBER NOT NULL DEFAULT 83;
    too_small_input EXCEPTION;
    too_big_input EXCEPTION;
BEGIN
    IF input < min_input THEN
        RAISE too_small_input;
    END IF;
    IF input > max_input THEN
        RAISE too_big_input;
    END IF;
    FOR i IN 1..input
        LOOP
            result := result * i;
        END LOOP;
    dbms_output.put_line(input || '! = ' || result);
EXCEPTION
    WHEN too_small_input THEN
        dbms_output.put_line('Input should be at least ' || min_input);
    WHEN too_big_input THEN
        dbms_output.put_line('Input should be at most ' || max_input);
END;
