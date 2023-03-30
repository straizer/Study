#include "array.h"
#include <stdlib.h>

Array createArray(size_t size)
{
    Array array;
    array.size = size;
    array.items = malloc(size * sizeof(int));
    return array;
}

void printArray(Array array)
{
    for (size_t i = 0; i < array.size; i++)
        printf("%d ", array.items[i]);
}

void deleteArray(Array array)
{
    free(array.items);
}