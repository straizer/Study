#include "reader.h"

#include "error.h"

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
    return result;
}

char readChar(FILE* stream)
{
    int result = fgetc(stream);
    if (result == EOF)
        error("ERROR WHILE READING");
    return result;
}