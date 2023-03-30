#include <stdio.h>
#include <math.h>

int main(void)
{
    printf("Enter number: ");
    
    int number;
    scanf("%d", &number);
    
    int last_to_check = floor(sqrt(abs(number)));
    for (int i = 2; i <= last_to_check; i++)
        if (number % i == 0)
        {
            printf("Number is not prime");
            return 0;
        }
    printf("Number is prime");
}