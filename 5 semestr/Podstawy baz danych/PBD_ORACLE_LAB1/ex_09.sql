-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
DECLARE
    input1             NUMBER NOT NULL := &x;
    input2             NUMBER NOT NULL := &x;
    a                  NUMBER;
    b                  NUMBER;
    tmp                NUMBER;
    min_input CONSTANT NUMBER NOT NULL DEFAULT 1;
    too_small_input EXCEPTION;
BEGIN
    IF input1 < min_input OR input2 < min_input THEN
        RAISE too_small_input;
    END IF;
    a := input1;
    b := input2;
    WHILE b != 0
        LOOP
            tmp := b;
            b := MOD(a, b);
            a := tmp;
        END LOOP;
    dbms_output.put_line('GCD(' || input1 || ', ' || input2 || ') = ' || a);
EXCEPTION
    WHEN too_small_input THEN
        dbms_output.put_line('Input should be at least ' || min_input);
END;
