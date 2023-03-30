#include <stdio.h>

int main(void)
{
    printf("Enter last number to sum: ");    

    int last;
    scanf("%d", &last);    

    int sum = 0;
    for (int i = 1; i <= last; i++)
        sum += i;
    printf("Sum of numbers from 1 to %d is %d", last, sum);
}