from commons import *


@repeat
def copy(counter: int, table_name: str, source_cursor: DictCursor, destination_cursor: DictCursor) -> int:
    random_id: int = fetch_random(source_cursor, f"SELECT id FROM {table_name};")
    # noinspection SqlMissingColumnAliases
    if fetch_one(destination_cursor, f"SELECT COUNT(*) FROM {table_name} WHERE id = {random_id};") == 0:
        random_record: Mapping[str, Any] = fetch_one(source_cursor,
                                                     f"SELECT * FROM {table_name} WHERE id = {random_id};")
        destination_cursor.execute(
            f"INSERT INTO {table_name} VALUES "
            f"({random_record['id']}, '{random_record['first_name']}',"
            f"'{random_record['last_name']}', '{random_record['address']}');")
        counter += 1
    return counter


@timing("times.txt")
@get_cursors_from_connections
def copy_table(cursors: Iterable[DictCursor], table_size: int, *verbs: str) -> None:
    run_repeated(copy, lambda _: table_size, cursors, table_size, *verbs)


@handle_connections_to_hosts("source-db", "destination-db")
def main(connections: CONNECTIONS_TYPE) -> None:
    run_for_each_table(copy_table, connections, "Copying", "copied")


if __name__ == "__main__":
    main()
