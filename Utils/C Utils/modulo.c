#include "modulo.h"

ullong addModulo(ullong a, ullong b, ullong modulo)
{
    ullong added = a + b;
    if (added >= a && added >= b)
        if (added >= modulo)
            return added - modulo;
        else
            return added;
    return a - (modulo - b);
}

ullong multiplyModulo(ullong a, ullong b, ullong modulo)
{
    ullong result = 0;
    ullong a_times_power_of_2 = a;
    for (ullong i = b; ; i /= 2)
    {
        if (i % 2 == 1)
        {
            result = addModulo(result, a_times_power_of_2, modulo);
            i--;
            if (!i)
                return result;
        }
        a_times_power_of_2 = addModulo(a_times_power_of_2, a_times_power_of_2, modulo);
    }
}

ullong powerModulo(ullong base, ullong exponent, ullong modulo)
{
    ullong result = 1;
    ullong base_to_power_of_2 = base;
    for (ullong i = exponent; ; i /= 2)
    {
        if (i % 2 == 1)
        {
            result = multiplyModulo(result, base_to_power_of_2, modulo);
            i--;
            if (!i)
                return result;
        }
        base_to_power_of_2 = multiplyModulo(base_to_power_of_2, base_to_power_of_2, modulo);
    }
}