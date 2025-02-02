from functools import wraps as _wraps
from time import time as _time
from typing import Any as _Any, Callable as _Callable, TypeVar as _TypeVar

T: _TypeVar = _TypeVar('T')


def repeat(func: _Callable[[int, _Any], int]) -> _Callable[[int, _Any], None]:
    @_wraps(func)
    def _wrapper(divisor: int, times: int, *args: _Any) -> None:
        counter: int = 0
        last_printed_counter: int = 0
        while counter < times:
            counter = func(counter, *args)
            if counter * divisor // times == divisor - 1 and times // divisor >= 10:
                divisor *= 10
            if last_printed_counter != counter and counter % (times // divisor) == 0:
                last_printed_counter = counter
                print(f"Progress: {counter * 100 / times}%")

    return lambda times, *args: _wrapper(10, times, *args)


def timing(dump_filename: str | None = None) -> _Callable[[_Callable[..., T]], _Callable[..., T]]:
    def _inner(func: _Callable[..., T]) -> _Callable[..., T]:
        @_wraps(func)
        def _wrapper(*args: _Any) -> T:
            start_time: float = _time()
            func(*args)
            total_time_in_seconds: float = _time() - start_time
            if dump_filename:
                with open(dump_filename, "a") as file:
                    # file.write(f"{func.__name__}{args} - {total_time_in_seconds} s\n")
                    file.write(f"{total_time_in_seconds}\n")
            total_time: float = total_time_in_seconds
            unit: str = "s"
            if total_time < 1:
                total_time *= 1000
                unit = "ms"
            elif total_time > 60:
                total_time /= 60
                unit = "min"
                if total_time > 60:
                    total_time /= 60
                    unit = "h"
            saved_info: str = f" - saved to file {dump_filename}" if dump_filename else ""
            print(f"Finished in {total_time:.2f} {unit} ({total_time_in_seconds} s{saved_info}).\n")

        return _wrapper

    return _inner
