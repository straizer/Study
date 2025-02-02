SELECT DISTINCT nazwisko, imie, kraj
FROM ksiazka
         NATURAL JOIN autor_tytul
         NATURAL JOIN autor
WHERE id_gat = (SELECT id_gat
                FROM ksiazka
                ORDER BY l_stron
                    FETCH FIRST ROW ONLY);
