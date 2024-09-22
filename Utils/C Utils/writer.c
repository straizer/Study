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

void writeNewLine(FILE* stream)
{
    write(stream, "\n");
}

void writeInt(FILE* stream, int data, const char* terminator)
{
    write(stream, "%d%s", data, terminator);
}

void writeString(FILE* stream, const char* data, const char* terminator)
{
    write(stream, "%s%s", data, terminator);
}

void writeULLong(FILE* stream, ullong data, const char* terminator)
{
    write(stream, "%llu%s", data, terminator);
}

void writeChar(FILE* stream, char data, const char* terminator)
{
    write(stream, "%c%s", data, terminator);
}

void writeUShort(FILE* stream, ushort data, const char* terminator)
{
    write(stream, "%hu%s", data, terminator);
}