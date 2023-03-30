#include <stdio.h>

int main(void)
{
    int number;

    while (1)
    {
        printf("Enter positive number or zero (0) to finish: ");
        scanf("%d", &number);
        
        if (number == 0)
            return 0;
            
        int factorial = 1;
        for (int i = 2; i <= number; i++)
            factorial *= i;
        
        printf("Factorial of %d is %d\n\n", number, factorial);
    }
}