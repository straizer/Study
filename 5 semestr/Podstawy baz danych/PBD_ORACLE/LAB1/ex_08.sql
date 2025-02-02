SET SERVEROUTPUT ON
DECLARE
    TYPE student IS RECORD
                    (
                        id       NUMBER(4),
                        nazwisko VARCHAR2(30),
                        miasto   VARCHAR2(30),
                        telefon  VARCHAR2(9)
                    );

    test_student student;

    FUNCTION create_student(id IN test_student.id%TYPE,
                            nazwisko IN test_student.nazwisko%TYPE,
                            miasto IN test_student.miasto%TYPE,
                            telefon IN test_student.telefon%TYPE)
        RETURN student IS
        result student;
    BEGIN
        SELECT id, nazwisko, miasto, telefon INTO result FROM dual;
        RETURN result;
    END;

    PROCEDURE print_student(s IN student) IS
    BEGIN
        dbms_output.put_line('id: ' || s.id);
        dbms_output.put_line('nazwisko: ' || s.nazwisko);
        dbms_output.put_line('miasto: ' || s.miasto);
        dbms_output.put_line('telefon: ' || s.telefon);
    END;
BEGIN
    test_student := create_student(id => 1, nazwisko => 'Kowalski', miasto => 'Warszawa', telefon => '123456789');
    print_student(s => test_student);
END;
