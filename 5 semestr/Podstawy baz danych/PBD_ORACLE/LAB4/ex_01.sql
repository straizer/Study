CREATE OR REPLACE FUNCTION calculate_total_price(genre_id IN gatunek.id_gat%TYPE) RETURN NUMBER IS
    total_price NUMBER;
BEGIN
    SELECT SUM(cena)
    INTO total_price
    FROM gatunek
             NATURAL JOIN ksiazka
    WHERE id_gat = genre_id;
    RETURN total_price;
END;

SELECT calculate_total_price(2)
FROM dual;
