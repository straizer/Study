#include "reader.h"

#include "error.h"
#include "writer.h"
#include "converter.h"

int readInt(FILE* stream)
{
    int result;
    if (fscanf(stream, "%d", &result) == EOF)
        error("ERROR WHILE READING");
    return result;
}

char* readString(FILE* stream)
{
    static char result[256];
    if (fscanf(stream, "%[^\n]", result) == EOF)
        error("ERROR WHILE READING");
    while (getchar() != '\n');
    return result;
}

char* readKnownSizeString(FILE* stream, size_t size)
{
    char* result = calloc(size, sizeof(char) + 1);
    if (!result)
        error("ERROR WHILE READING");
    for (size_t i = 0; i < size; i++)
        result[i] = readChar(stream);
    while (getchar() != '\n');
    return result;
}

char readChar(FILE* stream)
{
    int result = fgetc(stream);
    if (result == EOF)
        error("ERROR WHILE READING");
    return result;
}

ullong tryReadULLong(FILE* stream)
{
    while (1)
    {
        char* string = readString(stream);
        ConvertToULLongResult converted = convertStrToULLong(string);
        if (converted.status == SUCCESS)
            return converted.number;
        write(cout, "'%s' is invalid unsigned 64-bit integer (%s).\nTry again: ", string, status_names[converted.status]);
    }
}

ushort tryReadUShort(FILE* stream)
{
    while (1)
    {
        char* string = readString(stream);
        ConvertToUShortResult converted = convertStrToUShort(string);
        if (converted.status == SUCCESS)
            return converted.number;
        write(cout, "'%s' is invalid unsigned 16-bit integer (%s).\nTry again: ", string, status_names[converted.status]);
    }
}