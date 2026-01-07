#include "utils.h"

#include <errno.h>
#include <stdio.h>
#include <string.h>

enum {
    ERROR_BUFFERS_SLOTS = 8,
    ERROR_BUFFERS_SIZE = 256,
};

// cppcheck-suppress misra-c2012-1.4
static _Thread_local char error_buffers  // NOLINT(cppcoreguidelines-avoid-non-const-global-variables)
    [ERROR_BUFFERS_SLOTS][ERROR_BUFFERS_SIZE];
// cppcheck-suppress misra-c2012-1.4
static _Thread_local unsigned error_buffer_idx;  // NOLINT(cppcoreguidelines-avoid-non-const-global-variables)

static char* nextErrorBuffer(void) {
    char* const buf = error_buffers[error_buffer_idx % ERROR_BUFFERS_SLOTS];
    error_buffer_idx++;
    return buf;
}

const char* prefixErrno(const char* const prefix) {
    const int saved_errno = errno;

    char tmp[ERROR_BUFFERS_SIZE];
    const char* const msg = strerror_r(saved_errno, tmp, sizeof(tmp));

    char* const out = nextErrorBuffer();

    if (prefix != nullptr && prefix[0] != '\0') {
        (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s: %s", prefix, msg);
    } else {
        (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s", msg);
    }

    errno = saved_errno;
    return out;
}

const char* prefixError(const char* const prefix, const char* const message) {
    if (prefix == nullptr || prefix[0] == '\0') {
        return message;
    }

    char* const out = nextErrorBuffer();

    if (message == nullptr) {
        (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s", prefix);
        return out;
    }

    (void)snprintf(out, ERROR_BUFFERS_SIZE, "%s: %s", prefix, message);
    return out;
}