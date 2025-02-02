from re import sub as _sub, Pattern as _Pattern, compile as _compile
from typing import Iterable, Any, Mapping, Callable as _Callable

from pymysql import connect as _connect

from mysql_utils import *
from mysql_utils import _CALLABLE_WITH_CONNECTIONS_TYPE
from utils import *

_LETTERS_PATTERN: _Pattern = _compile("[A-Za-z]+")


def handle_connections_to_hosts(*hosts: str) -> _Callable[[_CALLABLE_WITH_CONNECTIONS_TYPE[T]], _Callable[..., T]]:
    return handle_connections(*[_create_connect_lambda(host) for host in hosts])


def run_for_each_table(func: _Callable[[CONNECTIONS_TYPE, int, str, str], None], connections: CONNECTIONS_TYPE,
                       running_verb: str, finished_verb: str) -> None:
    print(f"Copying {running_verb}...")
    for table_size in _get_table_sizes(connections):
        func(connections, table_size, running_verb, finished_verb)
    print(f"Database {finished_verb}.")


def run_repeated(func: _Callable[[int, Any], None], repeats_count_extractor: _Callable[[str], int],
                 cursors: Iterable[DictCursor], table_size: int, running_verb: str, finished_verb: str) -> None:
    table_name: str = f"`{table_size}records`"
    print(f"{running_verb} table {table_name}...")
    # noinspection PyArgumentList
    func(repeats_count_extractor(table_name), table_name, *cursors)
    print(f"Table {table_name} {finished_verb}.")


def _get_table_sizes(connections: CONNECTIONS_TYPE) -> Iterable[int]:
    with connections.__iter__().__next__().cursor(DictCursor) as cursor:
        cursor.execute("SHOW TABLES;")
        table_sizes: Iterable[int] = sorted(map(_size_extractor, cursor.fetchall()))
        print(f"Table sizes in database: {table_sizes}\n")
        return table_sizes


def _size_extractor(row: Mapping[str, Any]) -> int:
    return int(_sub(_LETTERS_PATTERN, "", row["Tables_in_ex_4"]))


def _create_connect_lambda(host: str) -> _Callable[[], Connection]:
    return lambda: _connect(host=host, port=3306, user="root", password="example", database="ex_4")
