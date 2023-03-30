#include "array.h"

Array* getInputArray(size_t);
Array* findDuplicates(Array*);

int main(void)
{
    Array* array = getInputArray(10);
    Array* duplicates_array = findDuplicates(array);
    
    printf("Numbers with more than 1 occurence: ");
    printArray(duplicates_array, ' ');
        
    deleteArray(array);
    deleteArray(duplicates_array);
}

Array* getInputArray(size_t size)
{
    printf("Enter %lu numbers separated by space: ", size);
    Array* array = createArray(size);
    for (size_t i = 0; i < size; i++)
        scanf("%d", &array->items[i]);
    return array;
}

Array* findDuplicates(Array* array)
{
    int first_occurence_array[array->size], foa_counter = 0;
    int multiple_occurence_array[array->size/2], moa_counter = 0;
    for (int i = 0; i < array->size; i++)
    {
        int bool_number_in_foa = 0;
        for (int j = 0; j < foa_counter; j++)
            if (first_occurence_array[j] == array->items[i])
            {
                bool_number_in_foa = 1;
                break;
            }
            
        if (bool_number_in_foa)
        {
            int bool_number_in_moa = 0;
            for (int j = 0; j < moa_counter; j++)
                if (multiple_occurence_array[j] == array->items[i])
                {
                    bool_number_in_moa = 1;
                    break;
                }
            if (!bool_number_in_moa)
            {
                multiple_occurence_array[moa_counter] = array->items[i];
                moa_counter++;
            }
        }
        else
        {
            first_occurence_array[foa_counter] = array->items[i];
            foa_counter++;
        }
    }
    Array* duplicates_array = createArray(moa_counter);
    memcpy(duplicates_array->items, multiple_occurence_array, moa_counter * sizeof(int));
    return duplicates_array;
}