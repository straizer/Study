SELECT "DANE CZYTELNIKA", COUNT(g_nazwa) AS "ILOŚĆ WYPOŻYCZANYCH GATUNKÓW"
FROM (SELECT DISTINCT imie || ' ' || nazwisko AS "DANE CZYTELNIKA", g_nazwa
      FROM czytelnik
               NATURAL JOIN wypozyczenia
               NATURAL JOIN ksiazka
               NATURAL JOIN gatunek)
GROUP BY "DANE CZYTELNIKA"
ORDER BY "ILOŚĆ WYPOŻYCZANYCH GATUNKÓW" DESC;
