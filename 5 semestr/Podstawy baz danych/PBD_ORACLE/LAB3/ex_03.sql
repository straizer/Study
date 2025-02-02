CREATE TABLE autor_log AS
SELECT *
FROM autor
WHERE 1 = 0;

ALTER TABLE autor_log
    ADD log_operation VARCHAR2(10);

CREATE OR REPLACE TRIGGER autor_modification_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON autor
    FOR EACH ROW
BEGIN
    IF INSERTING THEN
        INSERT INTO autor_log VALUES (:new.id_aut, :new.nazwisko, :new.imie, :new.kraj, 'INSERT');
    ELSIF UPDATING THEN
        INSERT INTO autor_log VALUES (:new.id_aut, :new.nazwisko, :new.imie, :new.kraj, 'UPDATE');
    ELSIF DELETING THEN
        INSERT INTO autor_log VALUES (:old.id_aut, :old.nazwisko, :old.imie, :old.kraj, 'DELETE');
    END IF;
END;

INSERT INTO autor
VALUES (16, 'Romaniak', 'Hubert', 'Polska');

UPDATE autor
SET id_aut = 17
WHERE id_aut = 16;

DELETE
FROM autor
WHERE id_aut = 17;