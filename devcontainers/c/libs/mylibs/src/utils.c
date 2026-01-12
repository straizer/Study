#include "./utils.h"

#include <unistd.h>

#include <libexplain/close.h>

#include "./errors.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION_VOID(closeFileDescriptor, int* file_descriptor) {
    if (*file_descriptor < 0) {
        return closeFileDescriptorErr("file descriptor is negative");
    }

    if (close(*file_descriptor) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_close(buffer, ERROR_BUFFER_SIZE, *file_descriptor);
        return closeFileDescriptorErr(buffer);
    }

    *file_descriptor = -1;

    return closeFileDescriptorOk();
}