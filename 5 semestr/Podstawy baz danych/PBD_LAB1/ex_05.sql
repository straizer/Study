-- noinspection SqlMissingColumnAliasesForFile

-- v1: using CTE
-- noinspection SqlMissingColumnAliases
WITH count_per_genre AS (SELECT id_gat, COUNT(*) AS count
                         FROM wypozyczenia
                                  NATURAL JOIN ksiazka
                         GROUP BY id_gat)
SELECT g_nazwa AS gatunek
FROM count_per_genre
         NATURAL JOIN gatunek
WHERE count > (SELECT SUM(count) FROM count_per_genre) /
              (SELECT COUNT(*) FROM gatunek);

-- v2: using group functions with over
SELECT g_nazwa AS gatunek
FROM (SELECT id_gat, count, SUM(count) OVER () / (SELECT COUNT(*) FROM gatunek) AS average
      FROM (SELECT id_gat, COUNT(*) AS count
            FROM wypozyczenia
                     NATURAL JOIN ksiazka
            GROUP BY id_gat))
         NATURAL JOIN gatunek
WHERE count > average;

-- v3: using group functions with over partition by
SELECT g_nazwa AS gatunek
FROM (SELECT DISTINCT id_gat,
                      COUNT(*) OVER (PARTITION BY id_gat)               AS count_per_genre,
                      COUNT(*) OVER () / (SELECT COUNT(*) FROM gatunek) AS average_count
      FROM wypozyczenia
               NATURAL JOIN ksiazka)
         NATURAL JOIN gatunek
WHERE count_per_genre > average_count;
