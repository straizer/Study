#include "array.h"

Array* getInputArray(size_t);
void insertSort(Array*);
void shiftArrayElement(Array*, int, int);

int main(void)
{
    Array* array = getInputArray(10);
    insertSort(array);
    printf("Sorted numbers: ");
    printArray(array, ' ');
    deleteArray(array);
}

Array* getInputArray(size_t size)
{
    printf("Enter %lu numbers separated by space: ", size);
    Array* array = createArray(size);
    for (size_t i = 0; i < size; i++)
        scanf("%d", &array->items[i]);
    return array;
}

void insertSort(Array* array)
{
    for (int i = 1; i < array->size; i++)
        for (int j = 0; j < i; j++)
            if (array->items[i] < array->items[j])
            {
                shiftArrayElement(array, i, j);
                break;
            }
}

void shiftArrayElement(Array* array, int from_index, int to_index)
{
    int element_to_insert = array->items[from_index];
    for (int i = from_index - 1; i >= to_index; i--)
        array->items[i + 1] = array->items[i];
    array->items[to_index] = element_to_insert;
}