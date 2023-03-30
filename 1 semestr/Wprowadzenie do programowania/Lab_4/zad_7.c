#include "array.h"

Array* getInputArray(size_t);
void selectionSort(Array*);
int findMinIndex(Array*, int);
void swapElements(Array*, int, int);

int main(void)
{
    Array* array = getInputArray(10);
    selectionSort(array);
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

void selectionSort(Array* array)
{
    for (int i = 0; i < array->size; i++)
    {
        int min_index = findMinIndex(array, i);
        swapElements(array, i, min_index);
    }
}

int findMinIndex(Array* array, int start_index)
{
    int min_index = start_index;
    for (int i = start_index + 1; i < array->size; i++)
        if (array->items[i] < array->items[min_index])
            min_index = i;
    return min_index;
}

void swapElements(Array* array, int index_1, int index_2)
{
    int temp = array->items[index_1];
    array->items[index_1] = array->items[index_2];
    array->items[index_2] = temp;
}