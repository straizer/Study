SET SERVEROUTPUT ON
DECLARE
    id        ksiazka.id_ks%TYPE;
    title     ksiazka.tytul%TYPE;
    price     ksiazka.cena%TYPE;
    new_price ksiazka.cena%TYPE;
BEGIN
    LOOP
        SELECT id_ks, tytul, cena
        INTO id, title, price
        FROM ksiazka
        ORDER BY cena
            FETCH FIRST ROW ONLY;
        new_price := price * 1.05;
        EXIT WHEN new_price > 300;
        UPDATE ksiazka SET cena = new_price WHERE id_ks = id;
        dbms_output.put_line(RPAD(title, 20, ' ') || TO_CHAR(price, '999D99') || ' -> ' ||
                             TO_CHAR(new_price, '999D99'));
    END LOOP;
END;
