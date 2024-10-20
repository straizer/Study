-- noinspection SqlResolveForFile @ any/"dbms_output"

SET SERVEROUTPUT ON
DECLARE
    TYPE author_type IS RECORD
                        (
                            id_aut   autor.id_aut%TYPE,
                            nazwisko autor.nazwisko%TYPE,
                            imie     autor.imie%TYPE,
                            kraj     autor.kraj%TYPE
                        );
    CURSOR authors IS
        SELECT *
        FROM autor;
    author1 author_type;
    author2 autor%ROWTYPE;

    PROCEDURE print_author(author IN autor%ROWTYPE) IS
    BEGIN
        dbms_output.put_line('id_aut=' || author.id_aut || ',' || 'nazwisko=' || author.nazwisko || ',' ||
                             'imie=' || author.imie || ',' || 'kraj=' || author.kraj);
    END;
BEGIN
    IF NOT authors%ISOPEN THEN
        OPEN authors;
    END IF;
    -- noinspection GrazieInspection
    dbms_output.put_line('USING RECORD');
    LOOP
        FETCH authors INTO author1;
        EXIT WHEN authors%NOTFOUND;
        print_author(author => author1);
    END LOOP;
    IF authors%ISOPEN THEN
        CLOSE authors;
    END IF;

    IF NOT authors%ISOPEN THEN
        OPEN authors;
    END IF;
    -- noinspection SpellCheckingInspection
    dbms_output.put_line('USING ROWTYPE');
    LOOP
        FETCH authors INTO author2;
        EXIT WHEN authors%NOTFOUND;
        print_author(author => author2);
    END LOOP;
    IF authors%ISOPEN THEN
        CLOSE authors;
    END IF;

    -- noinspection GrazieInspection
    dbms_output.put_line('USING FOR LOOP');
    FOR author3 IN authors
        LOOP
            print_author(author => author3);
        END LOOP;
END;
