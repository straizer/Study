SET SERVEROUTPUT ON
CREATE OR REPLACE PROCEDURE get_oldest_published_book IS
    publisher wydawnictwo.w_nazwa%TYPE;
    title     ksiazka.tytul%TYPE;
    authors   VARCHAR2(100);
BEGIN
    SELECT w_nazwa, tytul, LISTAGG(nazwisko, ', ') WITHIN GROUP (ORDER BY tytul)
    INTO publisher, title, authors
    FROM (SELECT DISTINCT tytul, id_aut, id_wyd
          FROM ksiazka
                   LEFT JOIN autor_tytul USING (id_ks)
          WHERE data_wyd = (SELECT MIN(data_wyd) FROM ksiazka))
             LEFT JOIN autor USING (id_aut)
             NATURAL JOIN wydawnictwo
    GROUP BY tytul, w_nazwa;
    dbms_output.put_line('Oldest published book: ' || title || ' - ' || authors || ' (' || publisher || ')');
EXCEPTION
    WHEN too_many_rows THEN
        dbms_output.put_line('There is more than one oldest published book.');
END;

CALL get_oldest_published_book();
