-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
DECLARE
    cena NUMBER NOT NULL := &cena;
    CURSOR książki(p_cena NUMBER) IS
        SELECT tytul                                                 AS tytuł,
               LISTAGG(nazwisko, ', ') WITHIN GROUP (ORDER BY tytul) AS autorzy,
               w_nazwa                                               AS wydawnictwo,
               TO_CHAR(data_zwr, 'dd-mm-yyyy')                       AS "DATA ZWROTU",
               cena
        FROM (SELECT DISTINCT tytul, id_aut, id_wyd, id_ks, cena
              FROM ksiazka
                       LEFT JOIN autor_tytul USING (id_ks))
                 LEFT JOIN autor USING (id_aut)
                 NATURAL JOIN wydawnictwo
                 NATURAL JOIN wypozyczenia
        WHERE data_wyp <= SYSDATE
          AND data_zwr >= SYSDATE
          AND cena >= p_cena
        GROUP BY tytul, w_nazwa, id_wyp, data_zwr, cena
        ORDER BY cena DESC;
BEGIN
    FOR książka IN książki(cena)
        LOOP
            EXIT WHEN książki%ROWCOUNT > 3;
            dbms_output.put_line('"' || książka.tytuł || '" - ' || NVL(książka.autorzy, '<brak autora>') || ' (' ||
                                 książka.wydawnictwo || '); data zwrotu: ' || książka."DATA ZWROTU" || '; cena: ' ||
                                 książka.cena);
        END LOOP;
END;
