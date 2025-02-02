SELECT f_nazwa AS format, COUNT(*) AS ilość, SUM(cena) AS cena
FROM ksiazka
         NATURAL JOIN format
GROUP BY f_nazwa;
