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
    const bool prefix_valid = stringIsValid(prefix);
    const bool message_valid = stringIsValid(message);

    char* const out = error_buffers[error_buffer_idx % ERROR_BUFFERS_SLOTS];
    error_buffer_idx++;

    (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s%s%s", prefix_valid ? prefix : "",
                   prefix_valid && message_valid ? ": " : "", message_valid ? message : "");

    return out;
}

bool stringIsValid(const char* const string) { return string != nullptr && string[0] != '\0'; }