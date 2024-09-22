#include "converter.h"

#include <stdlib.h>
#include <limits.h>
#include <errno.h>
#include <ctype.h>

ConvertToULLongResult convertStrToULLong(const char* string)
{
    if (string[0] == '\0' || isspace(string[0]))
        return (ConvertToULLongResult){ .status = INCONVERTIBLE, .number = 0 };
    char *end;
    errno = 0;
    bool is_negative = string[0] == '-';
    ullong number = strtoull(string, &end, 10);
    if (errno == ERANGE && number == ULLONG_MAX)
        return (ConvertToULLongResult){ .status = OVERFLOW, .number = number };
    if (*end != '\0')
        return (ConvertToULLongResult){ .status = INCONVERTIBLE, .number = number };
    if (is_negative)
        return (ConvertToULLongResult){ .status = UNDERFLOW, .number = number };
    return (ConvertToULLongResult){ .status = SUCCESS, .number = number };
}

ConvertToUShortResult convertStrToUShort(const char* string)
{
    ConvertToULLongResult long_result = convertStrToULLong(string);
    return (ConvertToUShortResult){
        .status = (long_result.status == SUCCESS && long_result.number > USHRT_MAX) ? OVERFLOW : long_result.status,
        .number = long_result.number
    };
}