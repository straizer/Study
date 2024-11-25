#include "common.h"

#include <string.h>
#include "error.h"
#include "my_string.h"

MyStringArray splitString(const char* string, const char* separators)
{
    size_t substring_count = countSubstrings(string, separators);
    MyStringArray result = createMyStringArray(substring_count);
    char* string_copy = copyString(string);
    string_copy = strtok(string_copy, separators);
    for (int i = 0; i < substring_count; i++)
    {
        result.items[i] = createFilledMyString(string_copy);
        string_copy = strtok(NULL, separators);
    }
    free(string_copy);
    return result;
}

size_t countSubstrings(const char* string, const char* separators)
{
    char* string_copy = copyString(string);
    size_t substring_count = 0;
    string_copy = strtok(string_copy, separators);
    while (string_copy != NULL)
    {
        substring_count++;
        string_copy = strtok(NULL, separators);
    }
    free(string_copy);
    return substring_count;
}

char* copyString(const char* string)
{
    char* string_copy = malloc(strlen(string) + 1);
    if (!string_copy)
        error("ERROR WHILE COPYING STRING");
    memcpy(string_copy, string, strlen(string) + 1);
    return string_copy;
}