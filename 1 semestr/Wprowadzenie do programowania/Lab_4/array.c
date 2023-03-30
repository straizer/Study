#include "array.h"

Array* createArray(size_t size)
{
    Array* array = malloc(sizeof(Array));
    array->size = size;
    array->items = malloc(size * sizeof(int));
    return array;
}

Array* copyArray(Array* array)
{
    Array* array_copy = createArray(array->size);
    memcpy(array_copy->items, array->items, array->size * sizeof(int));
    return array_copy;
}

void printArray(Array* array, char separator)
{
    for (size_t i = 0; i < array->size; i++)
        printf("%d%c", array->items[i], separator);
}

void deleteArray(Array* array)
{
    free(array->items);
    free(array);
}