#include "array.h"

typedef long long int lli;

lli getPositiveLongLongInt(void);
Array* convertNumberToIntArray(lli);
Array* findDuplicates(Array*);

int main(void)
{
    lli number = getPositiveLongLongInt();
    Array* digit_array = convertNumberToIntArray(number);
    Array* duplicates_array = findDuplicates(digit_array);
    
    printf("Digits with more than 1 occurence: ");
    printArray(duplicates_array, ' ');
        
    deleteArray(digit_array);
    deleteArray(duplicates_array);
}

lli getPositiveLongLongInt(void)
{
    printf("Enter whole number in range 1-9,223,372,036,854,775,807: ");
    lli number;
    while(1)
    {
        scanf("%lld", &number);
        if (number > 0)
            return number;
        printf("Number does not belong in that range. Try again: ");
    }
}

Array* convertNumberToIntArray(lli number)
{
    char char_array[20];
    sprintf(char_array, "%lld", number);
    size_t length;
    for (int i = 0; i < 20; i++)
        if (char_array[i] == '\0')
        {
            length = i;
            break;
        }
    Array* array = createArray(length);
    for (int i = 0; i < length; i++)
        array->items[i] = char_array[i] - '0';
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