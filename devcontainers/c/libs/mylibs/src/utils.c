#include "./utils.h"

#include <stdlib.h>
#include <unistd.h>

#include <libexplain/close.h>
#include <libexplain/malloc.h>

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

DEFINITION_RVALUE(utilsMalloc, void*, const size_t size) {
    void* const result = malloc(size);  // cppcheck-suppress misra-c2012-21.3
    if (result == nullptr) {
        char* const buffer = getErrorBuffer();
        explain_message_malloc(buffer, ERROR_BUFFER_SIZE, size);
        return utilsMallocErr(buffer);
    }

    return utilsMallocOk(result);
}

void utilsFree(void* const ptr) { free(ptr); /* cppcheck-suppress misra-c2012-21.3 */ }