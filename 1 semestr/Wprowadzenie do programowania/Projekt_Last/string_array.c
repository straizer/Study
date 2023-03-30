#include "string_array.h"

#include "error.h"

MyStringArray createMyStringArray(size_t size)
{
    MyStringArray array;
    array.size = size;
    array.items = calloc(size, sizeof(MyString));
    if (!array.items)
        error("ERROR WHILE CREATING MYSTRING ARRAY");
    return array;
}

MyStringArray copyMyStringArray(MyStringArray array)
{
    MyStringArray array_copy = createMyStringArray(array.size);
    for (int i = 0; i < array.size; i++)
        array_copy.items[i] = copyMyString(array.items[i]);
    return array_copy;
}

void writeMyStringArray(FILE* stream, MyStringArray array, const char* separator)
{
    for (size_t i = 0; i < array.size; i++)
        writeMyString(stream, array.items[i], separator);
}

void deleteMyStringArray(MyStringArray array)
{
    for (int i = 0; i < array.size; i++)
        deleteMyString(array.items[i]);
    free(array.items);
}