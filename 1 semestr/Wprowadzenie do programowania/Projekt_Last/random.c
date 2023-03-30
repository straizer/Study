#include "random.h"

#include "error.h"
#include "converter.h"
#include <time.h>
#include <string.h>
#include <math.h>

bool is_initialized = false;

void _initialize(void)
{
    int seed = time(NULL);
    if (seed == -1)
        error("UNABLE TO GET CURRENT TIME");
    srand(seed);
    is_initialized = true;
}

int randomInt(int min, int max)
{
    if (!is_initialized)
        _initialize();
    size_t range = max - min + 1;
    return rand() % range + min;
}

char randomChar(const char* range)
{
    return range[randomInt(0, strlen(range) - 1)];
}

char* randomString(const char* range, size_t length)
{
    char* string =  malloc((length + 1) * sizeof(char));
    if (!string)
        error("ERROR WHILE GENERATING RANDOM STRING");
    for (size_t i = 0; i < length; i++)
        string[i] = randomChar(range);
    string[length] = '\0';
    return string;
}

ullong randomULLong(ullong min, ullong max)
{
    int min_length = min ? floor(log10(min) + 1) : 1;
    int max_length = max ? floor(log10(max) + 1) : 1;
    int long_length = randomInt(min_length, max_length);
    while (true)
    {
        char* long_string = randomString("1234567890", long_length);
        ConvertToULLongResult long_number = convertStrToULLong(long_string);
        free(long_string);
        if (long_number.status != SUCCESS)
            continue;
        if (long_number.number >= min && long_number.number <= max)
            return long_number.number;
    }
}

