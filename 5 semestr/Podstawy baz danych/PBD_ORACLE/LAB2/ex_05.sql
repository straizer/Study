SET SERVEROUTPUT ON
DECLARE
    "NAZWA FORMATU" format.f_nazwa%TYPE;
    ilość           NUMBER;
    CURSOR liczniki(p_format format.f_nazwa%TYPE) IS
        SELECT f_nazwa, COUNT(id_for)
        FROM format
                 NATURAL JOIN ksiazka
        WHERE UPPER(f_nazwa) = UPPER(p_format)
        GROUP BY f_nazwa;
BEGIN
    IF NOT liczniki%ISOPEN THEN
        OPEN liczniki('ebook');
    END IF;
    LOOP
        FETCH liczniki INTO "NAZWA FORMATU", ilość;
        EXIT WHEN liczniki%NOTFOUND;
        dbms_output.put_line("NAZWA FORMATU" || ' - ' || ilość);
    END LOOP;
    IF liczniki%ISOPEN THEN
        CLOSE liczniki;
    END IF;
END;
