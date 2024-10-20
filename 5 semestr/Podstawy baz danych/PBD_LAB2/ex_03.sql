-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
ALTER SESSION SET nls_language = 'english';
DECLARE
    CURSOR data IS
        SELECT imie, nazwisko, data_wyp, tytul, f_nazwa
        FROM czytelnik
                 NATURAL JOIN wypozyczenia
                 NATURAL JOIN ksiazka
                 NATURAL JOIN format;
BEGIN
    FOR element IN data
        LOOP
            dbms_output.put_line(INITCAP(element.imie) || ' ' ||
                                 INITCAP(element.nazwisko) || ' ' ||
                                 TO_CHAR(element.data_wyp, 'dd') || ' ' ||
                                 UPPER(TRIM(TO_CHAR(element.data_wyp, 'month'))) || ' ' ||
                                 INITCAP(element.tytul) || ' ' ||
                                 LOWER(element.f_nazwa));
        END LOOP;
END;
