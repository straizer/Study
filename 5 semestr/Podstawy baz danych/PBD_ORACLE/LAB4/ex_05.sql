SET SERVEROUTPUT ON

CREATE OR REPLACE PACKAGE books IS
    FUNCTION get_formats_count(name autor.imie%TYPE, surname autor.nazwisko%TYPE) RETURN NUMBER;
    PROCEDURE print_readers_of_books_of_least_publishing_publisher;
END;

CREATE OR REPLACE PACKAGE BODY books IS

    FUNCTION get_formats_count(name autor.imie%TYPE, surname autor.nazwisko%TYPE) RETURN NUMBER IS
        result NUMBER;
    BEGIN
        SELECT COUNT(*)
        INTO result
        FROM (SELECT DISTINCT id_for
              FROM autor
                       LEFT JOIN autor_tytul USING (id_aut)
                       LEFT JOIN ksiazka USING (id_ks)
              WHERE imie = name
                AND nazwisko = surname);
        RETURN result;
    END;

    PROCEDURE print_readers_of_books_of_least_publishing_publisher IS
        CURSOR data IS SELECT tytul, nazwisko
                       FROM ksiazka
                                NATURAL JOIN wypozyczenia
                                NATURAL JOIN czytelnik
                       WHERE id_wyd = ANY (SELECT id_wyd
                                           FROM ksiazka
                                           GROUP BY id_wyd
                                           HAVING COUNT(*) = (SELECT MIN(COUNT(*)) FROM ksiazka GROUP BY id_wyd));
    BEGIN
        dbms_output.put_line(RPAD('BOOK', 15, ' ') || '| READER SURNAME');
        dbms_output.put_line(LPAD('-', 15, '-') || RPAD('|', 15, '-'));
        FOR entry IN data
            LOOP
                dbms_output.put_line(RPAD(INITCAP(entry.tytul), 15, ' ') || '| ' || INITCAP(entry.nazwisko));
            END LOOP;
    END;

END;

SELECT books.get_formats_count('Juan', 'Aseron')
FROM dual;

CALL books.print_readers_of_books_of_least_publishing_publisher();
