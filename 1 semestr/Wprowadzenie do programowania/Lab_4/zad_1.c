#include "array.h"

Array* getInputArray(size_t);

int main(void)
{
    Array* array = getInputArray(10);
    Array* array_copy = copyArray(array);
    
    printf("Numbers in array copy: ");
    printArray(array_copy, ' ');
        
    deleteArray(array);
    deleteArray(array_copy);
}

Array* getInputArray(size_t size)
{
    printf("Enter %lu numbers separated by space: ", size);
    Array* array = createArray(size);
    for (size_t i = 0; i < size; i++)
        scanf("%d", &array->items[i]);
    return array;
}