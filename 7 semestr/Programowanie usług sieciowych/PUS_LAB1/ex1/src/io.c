#include "io.h"

#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>

void print(const char* const format, ...) {
    va_list args;
    va_start(args, format);
    if (vprintf(format, args) < 0) {
        perror("vprintf()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    va_end(args);
}