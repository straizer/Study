#include <stdio.h>

int main(void)
{
    printf("Numbers from 1 to 200, divisible by 13: ");

    for (int i = 1; i <= 200; i++)
        if (i % 13 == 0)
            printf("%d ", i);
}