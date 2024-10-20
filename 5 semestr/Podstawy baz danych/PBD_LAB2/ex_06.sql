-- noinspection SqlResolveForFile

SET SERVEROUTPUT ON
DECLARE
    CURSOR książki IS
        SELECT id_ks, INITCAP(tytul) AS tytuł, cena, w_nazwa AS wydawnictwo
        FROM ksiazka
                 NATURAL JOIN wydawnictwo
            FOR UPDATE;
    mnożnik   NUMBER(3, 2);
    nowa_cena NUMBER;
    książka2  książki%ROWTYPE;

    FUNCTION get_mnożnik(książka książki%ROWTYPE) RETURN NUMBER(3, 2) IS
    BEGIN
        IF książka.wydawnictwo = 'Litera' THEN
            RETURN 1.1;
        ELSE
            RETURN 1.05;
        END IF;
    END;
BEGIN
    dbms_output.put_line('--------------WHERE CURRENT OF--------------');
    FOR książka1 IN książki
        LOOP
            mnożnik := get_mnożnik(książka => książka1);
            UPDATE ksiazka SET cena = cena * mnożnik WHERE CURRENT OF książki;
            SELECT cena INTO nowa_cena FROM ksiazka WHERE id_ks = książka1.id_ks;
            dbms_output.put_line(książka1.tytuł || ': ' || książka1.cena || ' -> ' || nowa_cena);
        END LOOP;

    dbms_output.put_line('--------------RETURNING INTO--------------');
    IF NOT książki%ISOPEN THEN
        OPEN książki;
    END IF;
    LOOP
        FETCH książki INTO książka2;
        EXIT WHEN książki%NOTFOUND;
        mnożnik := get_mnożnik(książka => książka2);
        UPDATE ksiazka SET cena = cena * mnożnik WHERE id_ks = książka2.id_ks RETURNING cena INTO nowa_cena;
        dbms_output.put_line(książka2.tytuł || ': ' || książka2.cena || ' -> ' || nowa_cena);
    END LOOP;
    IF NOT książki%ISOPEN THEN
        CLOSE książki;
    END IF;
END;
