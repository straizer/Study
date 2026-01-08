#include "../include/mylibs/utils.h"

#include <errno.h>
#include <stdio.h>
#include <string.h>

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    ERROR_BUFFERS_SLOTS = 8,
    ERROR_BUFFERS_SIZE = 256,
};

// cppcheck-suppress misra-c2012-1.4
static _Thread_local char error_buffers  // NOLINT(cppcoreguidelines-avoid-non-const-global-variables)
    [ERROR_BUFFERS_SLOTS][ERROR_BUFFERS_SIZE];
// cppcheck-suppress misra-c2012-1.4
static _Thread_local unsigned error_buffer_idx;  // NOLINT(cppcoreguidelines-avoid-non-const-global-variables)

const char* formatError(const char* first, const char* prefix_second, const char* second, const char* suffix_second);

/* ------------------------------------------ Public function definitions ------------------------------------------ */

const char* prefixErrno(const char* const prefix) {
    const int saved_errno = errno;

    char tmp[ERROR_BUFFERS_SIZE];
    const char* const message = strerror_r(saved_errno, tmp, sizeof(tmp));
    const char* const out = prefixError(prefix, message);

    errno = saved_errno;
    return out;
}

const char* prefixError(const char* const prefix, const char* const message) {
    return formatError(prefix, ": ", message, nullptr);
}

const char* errorDuring(const char* const primary_prefix, const char* const primary_message,
                        const char* const secondary) {
    return formatError(prefixError(primary_prefix, primary_message), " (while handling error: ", secondary, ")");
}

bool stringIsValid(const char* const string) { return string != nullptr && string[0] != '\0'; }

/* ------------------------------------------ Private function definitions ------------------------------------------ */

const char* formatError(const char* const first, const char* const prefix_second, const char* const second,
                        const char* const suffix_second) {
    char* const out = error_buffers[error_buffer_idx % ERROR_BUFFERS_SLOTS];
    error_buffer_idx++;

    const bool first_valid = stringIsValid(first);
    const bool prefix_second_valid = stringIsValid(prefix_second);
    const bool second_valid = stringIsValid(second);
    const bool suffix_second_valid = stringIsValid(suffix_second);

    (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s%s%s%s", first_valid ? first : "",
                   first_valid && second_valid && prefix_second_valid ? prefix_second : "", second_valid ? second : "",
                   first_valid && second_valid && suffix_second_valid ? suffix_second : "");

    return out;
}