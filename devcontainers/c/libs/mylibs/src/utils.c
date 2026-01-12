#include "./utils.h"

#include <unistd.h>

#include <libexplain/close.h>

#include "./errors.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION_NULL(utilsClose, int* file_descriptor) {
    if (*file_descriptor < 0) {
        return utilsCloseErr("file descriptor is negative");
    }

    if (close(*file_descriptor) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_close(buffer, ERROR_BUFFER_SIZE, *file_descriptor);
        return utilsCloseErr(buffer);
    }

    *file_descriptor = -1;

    return utilsCloseOk();
}