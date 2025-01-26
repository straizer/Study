CREATE OR REPLACE FUNCTION get_net_price(book_id IN ksiazka.id_ks%TYPE) RETURN NUMBER IS
    gross_price ksiazka.cena%TYPE;
BEGIN
    BEGIN
        SELECT cena * 100 / 108
        INTO gross_price
        FROM ksiazka
        WHERE id_ks = book_id;
        RETURN gross_price;
    EXCEPTION
        WHEN no_data_found THEN
            dbms_output.put_line('No book with id: ' || book_id);
        WHEN too_many_rows THEN
            dbms_output.put_line('Multiple books with id: ' || book_id);
    END;
    RETURN -1;
END;

SELECT get_net_price(2)
FROM dual;

SELECT get_net_price(0)
FROM dual;
