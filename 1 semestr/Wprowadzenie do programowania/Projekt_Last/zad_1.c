#include "writer.h"
#include "reader.h"
#include "prime.h"
#include <math.h>

ullong readPossiblePrimeULLong(FILE*);
ushort readPositiveUShort(FILE*);
double calculateCorrectness(ushort);

int main(void)
{
    write(cout, "Enter odd integer in range (2, 2^64) to test for primality: ");
    ullong number = readPossiblePrimeULLong(cin);
    
    writeLine(cout, "\nError propability is expressed by (1/4)^k, where k is number of tests.");
    write(cout, "Enter number of tests to do: ");
    ushort test_no = readPositiveUShort(cin);
    
    write(cout, "\nNumber %llu is ", number);
    if (isPrime(number, test_no))
        write(cout, "prime with propability of %.17g%%.", calculateCorrectness(test_no));
    else
        write(cout, "composite.");
}

ullong readPossiblePrimeULLong(FILE* stream)
{
    while (true)
    {
        ullong number = tryReadULLong(stream);
        if (number < 2)
            writeLine(cout, "Primes are defined for numbers > 1.");
        else if (number % 2 == 0 && number != 2)
            writeLine(cout, "Number is even and > 2, so it's composite number.");
        else
            return number;
        write(cout, "Try again: ");
    }
}

ushort readPositiveUShort(FILE* stream)
{
    while (true)
    {
        ushort number = tryReadUShort(stream);
        if (number != 0)
            return number;
        writeLine(cout, "Number of tests must be > 0.");
        write(cout, "Try again: ");
    }
}

double calculateCorrectness(ushort test_no)
{   
    return (1 - pow(1./4, test_no)) * 100;
}