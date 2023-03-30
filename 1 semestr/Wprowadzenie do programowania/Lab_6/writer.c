#include "writer.h"

#include "error.h"
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>

void _write(FILE* stream, const char* format, va_list args)
{
    if (vfprintf(stream, format, args) < 0)
        error("ERROR WHILE WRITING");
}

void write(FILE* stream, const char* format, ...)
{
    va_list args;
    va_start(args, format);
    _write(stream, format, args);
    va_end(args);
}

void writeLine(FILE* stream, const char* format, ...)
{
    size_t size = strlen(format);
    char* data = malloc(size + 2);
    if (!data)
        error("ERROR WHILE WRITING");
    strcpy(data, format);
    data[size] = '\n';
    data[size + 1] = '\0';
    
    va_list args;
    va_start(args, format);
    _write(stream, data, args);
    va_end(args);
    
    free(data);
}

void writeInt(FILE* stream, int data, const char* terminator)
{
    write(stream, "%d%s", data, terminator);
}

void writeString(FILE* stream, const char* data, const char* terminator)
{
    write(stream, "%s%s", data, terminator);
}