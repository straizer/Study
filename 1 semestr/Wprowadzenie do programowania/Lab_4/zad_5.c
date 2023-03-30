#include "array.h"

Array* getInputArray(size_t);
void bubbleSort(Array*);
void swapElements(Array*, int, int);

int main(void)
{
    Array* array = getInputArray(10);
    bubbleSort(array);
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

void bubbleSort(Array* array)
{
    int b_wasSwapInIteration = 1;
    int pairsToCheck = array->size - 1;
    while (b_wasSwapInIteration)
    {
        b_wasSwapInIteration = 0;
        for (int i = 0; i < pairsToCheck; i++)
            if (array->items[i] > array->items[i + 1])
            {
                swapElements(array, i, i + 1);
                b_wasSwapInIteration = 1;
            }
        pairsToCheck--;
        if (pairsToCheck == 0)
            break;
    }
}

void swapElements(Array* array, int index_1, int index_2)
{
    int temp = array->items[index_1];
    array->items[index_1] = array->items[index_2];
    array->items[index_2] = temp;
}