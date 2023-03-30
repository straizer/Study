#include <stdio.h>

int main(void)
{
    printf("Enter first number: ");
    int bigger;
    scanf("%d", &bigger);
    
    printf("Enter second number: ");
    int smaller;
    scanf("%d", &smaller);
    
    bigger = abs(bigger);
    smaller = abs(smaller);
    
    if (bigger < smaller)
    {
        int temp = bigger;
        bigger = smaller;
        smaller = temp;
    }
    
    int rest;
    while (1)
    {
        rest = bigger % smaller;
        if (rest == 0)
            break;
        
        bigger = smaller;
        smaller = rest;
    }
    
    printf("Greatest common divisor: %d", smaller);
}