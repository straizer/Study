CREATE OR REPLACE PROCEDURE increase_book_prices(book_id IN ksiazka.id_ks%TYPE) IS
BEGIN
    UPDATE ksiazka
    SET cena = CASE
                   WHEN cena < 25 THEN
                       cena * 1.15
                   ELSE
                       cena * 1.1
        END
    WHERE id_ks = book_id;
END;

CALL increase_book_prices(2);
