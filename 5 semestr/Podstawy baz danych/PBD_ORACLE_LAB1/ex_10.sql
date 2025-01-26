-- noinspection SqlUnusedForFile

-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
DECLARE
    input              NUMBER NOT NULL := &x;
    n                  NUMBER NOT NULL := 1;
    n_minus_1          NUMBER NOT NULL := 0;
    tmp                NUMBER;
    min_input CONSTANT NUMBER NOT NULL DEFAULT 1;
    too_small_input EXCEPTION;
BEGIN
    IF input < min_input THEN
        RAISE too_small_input;
    END IF;
    dbms_output.put(input || ' first Fibonacci numbers: ' || n_minus_1);
    IF input = 1 THEN
        RETURN;
    END IF;
    dbms_output.put(', ' || n);
    IF input = 2 THEN
        RETURN;
    END IF;
    FOR i IN 3..input
        LOOP
            tmp := n;
            n := n + n_minus_1;
            n_minus_1 := tmp;
            dbms_output.put(', ' || n);
        END LOOP;
    dbms_output.new_line();
EXCEPTION
    WHEN too_small_input THEN
        dbms_output.put_line('Input should be at least ' || min_input);
END;
