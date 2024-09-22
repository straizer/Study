#include "prime.h"

#include "random.h"
#include "modulo.h"

typedef struct {
    ullong d;
    ushort s;
} DAndS;

DAndS expressNumberAs_2_To_s_Times_d(ullong number)
{
    ushort s = 0;
    while (true)
    {
        if (number % 2 != 0)
            return (DAndS){ .d = number, .s = s };
        number /= 2;
        s++;
    }
}

ullong getRandomCoprimeUULong(ullong number)
{
    ullong random;
    bool try_again;
    do
    {
        try_again = false;
        random = randomULLong(1, number - 1);
        for (int i = 2; i <= random; i++)
            if (random % i == 0 && number % i == 0)
            {
                try_again = true;
                break;
            }
    }
    while (try_again);
    return random;
}

bool isCompositeSingleTest(ullong number, DAndS d_and_s)
{
    ullong random = getRandomCoprimeUULong(number);
    ullong power_modulo = powerModulo(random, d_and_s.d, number);
    if (power_modulo == 1 || power_modulo == number - 1)
        return false;
    for (size_t i = 1; i <= d_and_s.s - 1; i++)
    {
        power_modulo = powerModulo(power_modulo, 2, number);
        if (power_modulo == number - 1)
            return false;
    }
    return true;
}

bool isComposite(ullong number, ushort test_no)
{
    DAndS d_and_s = expressNumberAs_2_To_s_Times_d(number - 1);
    for (size_t i = 0; i < test_no; i++)
        if (isCompositeSingleTest(number, d_and_s))
            return true;
    return false;
}

bool isPrime(ullong number, ushort test_no)
{
    return !isComposite(number, test_no);
}