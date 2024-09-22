#include "my_string.h"

#include "error.h"
#include <string.h>
#include "writer.h"
#include "reader.h"

MyString createMyString(size_t size)
{
    MyString string;
    string.size = size;
    string.value = calloc(size, sizeof(char));
    if (!string.value)
        error("ERROR WHILE CREATING MYSTRING");
    return string;
}

MyString createFilledMyString(const char* str)
{
    MyString string = createMyString(strlen(str) + 1);
    memcpy(string.value, str, string.size);
    return string;
}

MyString copyMyString(MyString string)
{
    MyString string_copy = createMyString(string.size);
    memcpy(string_copy.value, string.value, string.size * sizeof(char));
    return string_copy;
}

void writeMyString(FILE* stream, MyString string, const char* treminator)
{
    writeString(stream, string.value, treminator);
}

MyString readMyString(FILE* stream, size_t size)
{
    MyString string = createMyString(size);
    char* read_string = readKnownSizeString(stream, size);
    memcpy(string.value, read_string, string.size * sizeof(char));
    free(read_string);
    return string;
}

void deleteMyString(MyString string)
{
    free(string.value);
}