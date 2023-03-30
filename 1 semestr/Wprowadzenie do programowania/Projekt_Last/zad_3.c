#include "writer.h"
#include "reader.h"
#include "prime.h"
#include "ushort_array.h"
#include "common.h"

void decode(UShortArray);

int main(void)
{
    write(cout, "Enter number of ciphers to decode: ");
    ushort string_count = tryReadUShort(cin);
    for (size_t i = 0; i < string_count; i++)
    {
        write(cout, "Enter cipher to decode (numbers separated by spaces in one line): ");
        UShortArray numbers = readUShortArray(cin, " ");
        decode(numbers);
        deleteUShortArray(numbers);
    }
}

void decode(UShortArray numbers)
{
    bool prime_found = false;
    for (size_t i = 121; i < 150; i += 2)
    {
        bool rest_is_letter_for_all = true;
        if (!isPrime(i, 10))
            continue;
        for (size_t j = 0; j < numbers.size; j++)
            if (numbers.items[j] % i < 65 || numbers.items[j] % i > 90)
            {
                rest_is_letter_for_all = false;
                break;
            }
        if (rest_is_letter_for_all)
        {
            writeLine(cout, "Decrypting prime: %d", i);
            write(cout, "Decrypted message: ");
            for (size_t j = 0; j < numbers.size; j++)
                writeChar(cout, numbers.items[j] % i, "");
            writeNewLine(cout);
            prime_found = true;
            break;
        }
    }
    if (!prime_found)
        writeLine(cout, "Ciper is unreadible for primes in range (120, 150).");
}