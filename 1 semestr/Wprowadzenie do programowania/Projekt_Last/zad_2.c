#include "writer.h"
#include "reader.h"
#include "error.h"
#include <stdlib.h>
#include <string.h>

ushort convertToArabic(const char*);
char* convertToRoman(ushort);

int main(void)
{
    write(cout, "Enter two Roman numbers in range (I...M), separated by space: ");
    char number1[15] = { 0 };
    char number2[15] = { 0 };
    scanf("%s", number1);
    scanf("%s", number2);
    
    ushort result = convertToArabic(number1) + convertToArabic(number2);
    char* roman_result = convertToRoman(result);
    write(cout, "%s", roman_result);
    
    free(roman_result);
}

ushort convertToArabic(const char* number)
{
    ushort result = 0;
    size_t last_digit = 0;
    for (int i = strlen(number) - 1; i >= 0; i--)
    {
        int digit;
        switch (number[i])
        {
            case 'I':
                digit = 1;
                break;
            case 'V':
                digit = 5;
                break;
            case 'X':
                digit = 10;
                break;
            case 'L':
                digit = 50;
                break;
            case 'C':
                digit = 100;
                break;
            case 'D':
                digit = 500;
                break;
            case 'M':
                digit = 1000;
                break;
            default:
                error("%c IS NOT VALID ROMAN NUMERAL", number[i]);
        }
        result += last_digit > digit ? -digit : digit;
        last_digit = digit;
    }
    return result;
}

char* convertToRoman(ushort number)
{
    char* result = calloc(15, sizeof(char));
    if (!result)
        error("ERROR WHILE CONVERTING TO ROMAN NUMERAL");
    ushort test_value;
    while (true)
    {
        test_value = number / 1000;
        if (test_value != 0)
        {
            strcat(result, "M");
            number -= 1000;
        }
        else
            break;
    }
    while (true)
    {
        test_value = number / 100;
        if (test_value == 9)
        {
            strcat(result, "CM");
            number -= 900;
        }
        else if (test_value > 4)
        {
            strcat(result, "D");
            number -= 500;
        }
        else if (test_value == 4)
        {
            strcat(result, "CD");
            number -= 400;
        }
        else if (test_value != 0)
        {
            strcat(result, "C");
            number -= 100;
        }
        else
            break;
    }
    while (true)
    {
        test_value = number / 10;
        if (test_value == 9)
        {
            strcat(result, "XC");
            number -= 90;
        }
        else if (test_value > 4)
        {
            strcat(result, "L");
            number -= 50;
        }
        else if (test_value == 4)
        {
            strcat(result, "XL");
            number -= 40;
        }
        else if (test_value != 0)
        {
            strcat(result, "X");
            number -= 10;
        }
        else
            break;
    }
    while (true)
    {
        test_value = number;
        if (test_value == 9)
        {
            strcat(result, "IX");
            number -= 9;
        }
        else if (test_value > 4)
        {
            strcat(result, "V");
            number -= 5;
        }
        else if (test_value == 4)
        {
            strcat(result, "IV");
            number -= 4;
        }
        else if (test_value != 0)
        {
            strcat(result, "I");
            number -= 1;
        }
        else
            break;
    }
    return result;
}
