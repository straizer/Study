from contextlib import ExitStack as _ExitStack
from functools import wraps as _wraps
from random import choice as _choice
from re import compile as _compile, IGNORECASE as _IGNORECASE, Pattern as _Pattern
from typing import Any as _Any, Iterable as _Iterable, Mapping as _Mapping, Callable as _Callable, Sequence as _Sequence

from pymysql import MySQLError as _MySQLError, Connection
from pymysql.cursors import DictCursor

from utils import T as _T

type CONNECTIONS_TYPE = _Iterable[Connection]
type _CALLABLE_WITH_CONNECTIONS_TYPE[R] = _Callable[[CONNECTIONS_TYPE, _Any], R] | _Callable[[CONNECTIONS_TYPE], R]
type _CONNECT_FUNCTION_TYPE = _Callable[[], Connection]

# noinspection RegExpAnonymousGroup
_QUERY_SELECT_PATTERN: _Pattern = _compile("SELECT (.+) FROM", _IGNORECASE)


def fetch_one(cursor: DictCursor, query: str) -> _Mapping[str, _Any] | _Any:
    cursor.execute(query)
    columns: str = _QUERY_SELECT_PATTERN.match(query).group(1)
    result: _Mapping[str, _Any] | None = cursor.fetchone()
    if result is not None and columns != "*" and "," not in columns:
        if "as" in columns.lower():
            return result[columns.lower().split("as")[1].strip()]
        return result[columns]
    return result


def fetch_random(cursor: DictCursor, query: str) -> _Mapping[str, _Any] | _Any:
    cursor.execute(query)
    columns: str = _QUERY_SELECT_PATTERN.match(query).group(1)
    query_result: _Sequence[_Mapping[str, _Any], ...] = cursor.fetchall()
    result: _Mapping[str, _Any] | None = _choice(query_result) if query_result else None
    if result is not None and columns != "*" and "," not in columns:
        if "as" in columns.lower():
            return result[columns.lower().split("as")[1].strip()]
        return result[columns]
    return result


def get_cursors_from_connections(func: _Callable[[_Iterable[DictCursor], _Any], _T]
                                 ) -> _Callable[[CONNECTIONS_TYPE, _Any], _T]:
    @_wraps(func)
    def _wrapper(connections: CONNECTIONS_TYPE, *args: _Any) -> _T:
        with _ExitStack() as stack:
            result: _T = func([stack.enter_context(connection.cursor(DictCursor)) for connection in connections], *args)
            for connection in connections:
                connection.commit()
            return result

    return _wrapper


def handle_connections(*connect_functions: _CONNECT_FUNCTION_TYPE
                       ) -> _Callable[[_CALLABLE_WITH_CONNECTIONS_TYPE[_T]], _Callable[..., _T]]:
    def _inner(func: _CALLABLE_WITH_CONNECTIONS_TYPE[_T]) -> _Callable[..., _T]:
        @_wraps(func)
        def _wrapper(*args: _Any) -> _T:
            connections: CONNECTIONS_TYPE | None = None
            try:
                connections = [_open_connection(connect_function) for connect_function in connect_functions]
                return func(connections, *args)
            except _MySQLError as e:
                print(f"\nMySQL operation error: {e}")
            finally:
                if connections:
                    for connection in connections:
                        if connection.open:
                            connection.close()
                        print(f"Connection ({connection.get_host_info()}) closed.")

        return _wrapper

    return _inner


def _open_connection(connect_function: _CONNECT_FUNCTION_TYPE) -> Connection:
    connection: Connection = connect_function()
    print(f"Connection established ({connection.get_host_info()}).")
    return connection
