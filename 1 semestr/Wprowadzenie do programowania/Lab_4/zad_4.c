#include "array.h"

int getPositiveInt(void);
int isPalindrome(int);
Array* convertNumberToIntArray(int);

int main(void)
{
    int number = getPositiveInt();
    printf("Number is%s a palindrome", isPalindrome(number) ? "" : " not");
}

int getPositiveInt(void)
{
    printf("Enter whole positive number (not whole number will be rounded down): ");
    int number;
    while(1)
    {
        scanf("%d", &number);
        if (number > 0)
            return number;
        printf("Number does not belong in that range. Try again: ");
    }
}

int isPalindrome(int number)
{
    int boolIsPalindrome = 1;
    Array* array = convertNumberToIntArray(number);
    for (int i = 0; i < array->size / 2; i++)
        if (array->items[i] != array->items[array->size - 1 - i])
            boolIsPalindrome = 0;
    deleteArray(array);
    return boolIsPalindrome;
}

Array* convertNumberToIntArray(int number)
{
    char char_array[20];
    sprintf(char_array, "%d", number);
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