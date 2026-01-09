#include "io.h"

#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>

/* ------------------------------------------------ Private members ------------------------------------------------ */

void print(FILE* stream, const char* format, va_list args);

/* ------------------------------------------ Public function definitions ------------------------------------------ */

void printOutput(const char* const format, ...) {
    va_list args;
    va_start(args, format);
    print(stdout, format, args);
    va_end(args);
}

void printError(const char* const format, ...) {
    va_list args;
    va_start(args, format);
    print(stderr, format, args);
    va_end(args);
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

void print(FILE* const stream, const char* const format, va_list args) {
    if (vfprintf(stream, format, args) < 0) {
        perror("vfprintf");
        exit(EXIT_FAILURE);  // cppcheck-suppress misra-c2012-21.8 // NOLINT(concurrency-mt-unsafe)
    }

    if (fputc('\n', stream) == EOF) {
        perror("fputc");
        exit(EXIT_FAILURE);  // cppcheck-suppress misra-c2012-21.8 // NOLINT(concurrency-mt-unsafe)
    }
}