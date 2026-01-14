#include "../include/mylibs/string.h"

#include <stdarg.h>
#include <stdio.h>
#include <string.h>

#include "./errors.h"
#include "./utils.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

DECLARATION_ALIGNED(stringNew, String, 64, size_t capacity)
DECLARATION_VOID(stringFormatVariadic, String* string, const char* format, va_list args)

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION_LVALUE(stringFormatNew, String, const size_t capacity, const char* const format, ...) {
    stringNewOutput string = stringNew(capacity);
    if (!string.ok) {
        return stringFormatNewErr(prefixError("stringNew", string.u.error));
    }

    va_list args;
    va_start(args, format);
    const stringFormatVariadicOutput output = stringFormatVariadic(&string.u.value, format, args);
    va_end(args);

    if (!output.ok) {
        stringDestroy(&string.u.value);
        return stringFormatNewErr(prefixError("stringFormatVariadic", output.u.error));
    }

    return stringFormatNewOk(&string.u.value);
}

String stringMove(String* const string) {
    const String out = *string;
    string->data = nullptr;
    return out;
}

void stringDestroy(const String* const string) { utilsFree(string->data); }

/* ------------------------------------------ Private function definitions ------------------------------------------ */

DEFINITION_LVALUE(stringNew, String, const size_t capacity) {
    const utilsMallocOutput buffer = utilsMalloc(capacity + 1U);
    if (!buffer.ok) {
        return stringNewErr(prefixError("utilsMalloc", buffer.u.error));
    }

    return stringNewOk(&(String){.data = buffer.u.value, .length = 0, .capacity = capacity + 1U});
}

DEFINITION_NULL(stringFormatVariadic, String* const string, const char* const format, va_list args) {
    const int result = vsnprintf(string->data, string->capacity, format, args);

    if (result < 0) {
        string->data[0] = '\0';
        return stringFormatVariadicErr(prefixError("vsnprintf", "formatting failed"));
    }

    if ((size_t)result >= string->capacity) {
        string->data[0] = '\0';
        return stringFormatVariadicErr(prefixError("vsnprintf", "output truncated"));
    }

    string->length = (size_t)result;
    return stringFormatVariadicOk();
}
