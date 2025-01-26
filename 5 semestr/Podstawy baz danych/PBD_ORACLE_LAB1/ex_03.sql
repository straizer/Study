SELECT f_nazwa AS formnat
FROM (SELECT id_ks, MONTHS_BETWEEN(SYSDATE, data_wyp) AS diff
      FROM wypozyczenia)
         NATURAL JOIN ksiazka
         NATURAL JOIN format
WHERE diff >= 0
  AND diff <= 3
GROUP BY f_nazwa
ORDER BY COUNT(f_nazwa) DESC
    FETCH FIRST ROW ONLY;
