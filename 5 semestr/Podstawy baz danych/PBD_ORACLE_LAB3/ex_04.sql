CREATE OR REPLACE TRIGGER autor_dorian_surname_trigger
    BEFORE INSERT
    ON autor
    FOR EACH ROW
    WHEN (new.nazwisko = 'Dorian')
BEGIN
    RAISE_APPLICATION_ERROR(-20000, '`Dorian` is invalid value for column `nazwisko`.');
END;

INSERT INTO autor
VALUES (16, 'Dorian', 'Hubert', 'Polska');