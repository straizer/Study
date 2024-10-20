SELECT COUNT(*) AS ilość
FROM ksiazka
WHERE id_wyd = (SELECT id_wyd
                FROM ksiazka
                ORDER BY data_wyd
                    FETCH FIRST ROW ONLY);
