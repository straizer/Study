-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
BEGIN
    FOR reader IN (SELECT imie, nazwisko, miasto FROM czytelnik ORDER BY nazwisko DESC)
        LOOP
            dbms_output.put_line(INITCAP(reader.imie) ||
                                 LPAD(UPPER(reader.nazwisko), 15, '*') ||
                                 LPAD(INITCAP(reader.miasto), 15, '*'));
        END LOOP;
END;
