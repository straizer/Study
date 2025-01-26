SET SERVEROUTPUT ON
DECLARE
    name    czytelnik.imie%TYPE;
    surname czytelnik.nazwisko%TYPE;
BEGIN
    WITH borrows_per_person AS (SELECT nazwisko, imie, COUNT(id_wyp) AS borrows
                                FROM czytelnik
                                         LEFT JOIN wypozyczenia USING (id_czyt)
                                GROUP BY nazwisko, imie)
    SELECT nazwisko, imie
    INTO surname, name
    FROM borrows_per_person
    WHERE borrows = (SELECT MIN(borrows) FROM borrows_per_person);
    dbms_output.put_line('Reader with the least number of borrows: ' || surname || ' ' || name);
EXCEPTION
    WHEN too_many_rows THEN
        dbms_output.put_line('More than one reader has the least number of borrows.');
END;

