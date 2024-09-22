#include "ushort_array.h"

#include "error.h"
#include <string.h>
#include "writer.h"
#include "converter.h"
#include "reader.h"

UShortArray createUShortArray(size_t size)
{
    UShortArray array;
    array.size = size;
    array.items = calloc(size, sizeof(ushort));
    if (!array.items)
        error("ERROR WHILE CREATING USHORT ARRAY");
    return array;
}

UShortArray copyUShortArray(UShortArray array)
{
    UShortArray array_copy = createUShortArray(array.size);
    memcpy(array_copy.items, array.items, array.size * sizeof(ushort));
    return array_copy;
}

void writeUShortArray(FILE* stream, UShortArray array, const char* separator)
{
    for (size_t i = 0; i < array.size; i++)
        writeUShort(stream, array.items[i], separator);
}

UShortArray readUShortArray(FILE* stream, const char* separators)
{
    char* string = readString(stream);
    MyStringArray substrings = splitString(string, separators);
    UShortArray array = createUShortArray(substrings.size);
    for (size_t i = 0; i < substrings.size; i++)
    {
        ConvertToUShortResult converted = convertStrToUShort(substrings.items[i].value);
        if (converted.status != SUCCESS)
            error("ERROR WHILE CONVERTING STRING TO 16-BIT INTEGER: '%s' IS INVALID VALUE", substrings.items[i].value);
        array.items[i] = converted.number;
    }
    deleteMyStringArray(substrings);
    return array;
}

void deleteUShortArray(UShortArray array)
{
    free(array.items);
}