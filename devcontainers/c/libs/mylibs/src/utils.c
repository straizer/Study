#include "../include/mylibs/utils.h"

#include <unistd.h>

#include "../include/mylibs/errors.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(closeFileDescriptor, nullptr_t)
closeFileDescriptorOutput closeFileDescriptor(const int file_descriptor) {
    if (close(file_descriptor) == -1) {
        return closeFileDescriptorErr(prefixErrno("close"));
    }

    return closeFileDescriptorOk(nullptr);
}