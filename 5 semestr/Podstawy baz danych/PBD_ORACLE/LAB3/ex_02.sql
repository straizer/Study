SET SERVEROUTPUT ON
DECLARE
    genre gatunek.g_nazwa%TYPE;
    name  ksiazka.tytul%TYPE;
    price ksiazka.cena%TYPE;
BEGIN
    SELECT g_nazwa, tytul, cena
    INTO genre, name, price
    FROM ksiazka
             NATURAL JOIN gatunek
    WHERE cena = (SELECT MAX(cena) FROM ksiazka);
    dbms_output.put_line('The most expensive book: ' || name || ' (' || genre || ') - ' || TO_CHAR(price, '000D00'));
EXCEPTION
    WHEN too_many_rows THEN
        dbms_output.put_line('More than one book is the most expensive.');
END;
