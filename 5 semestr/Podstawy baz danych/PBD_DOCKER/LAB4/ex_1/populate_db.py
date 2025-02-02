from faker import Faker

from commons import *

faker: Faker = Faker('pl_PL')


@repeat
def populate(counter: int, table_name: str, cursor: DictCursor) -> int:
    values = f"(DEFAULT, '{faker.first_name()}', '{faker.last_name()}', '{faker.address()}')"
    cursor.execute(f"INSERT INTO {table_name} VALUES {values};")
    return counter + 1


# noinspection SqlMissingColumnAliases
@timing()
@get_cursors_from_connections
def populate_table(cursors: Iterable[DictCursor], table_size: int, *verbs: str) -> None:
    run_repeated(
        populate,
        lambda table_name: table_size - fetch_one(cursors.__iter__().__next__(), f"SELECT COUNT(*) FROM {table_name};"),
        cursors, table_size, *verbs)


@handle_connections_to_hosts("source-db")
def main(connections: CONNECTIONS_TYPE) -> None:
    run_for_each_table(populate_table, connections, "Populating", "populated")


if __name__ == "__main__":
    main()
