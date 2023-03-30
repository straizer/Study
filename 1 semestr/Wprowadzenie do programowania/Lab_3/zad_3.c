#include <stdio.h>
#include <math.h>

int main(void)
{
    printf("Enter number: ");
    
    int number;
    scanf("%d", &number);
    
    printf("Divisors of %d: ", number);
    
    int last_to_check = floor(sqrt(abs(number)));
    for (int i = 1; i <= last_to_check; i++)
        if (number % i == 0)
            printf(i != abs(number/i) ? ("%d %d ") : ("%d "), i, abs(number/i));
}