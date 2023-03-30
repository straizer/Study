#include <stdio.h>
#include <math.h>

int main(void)
{
    printf("How much numbers will be entered: ");
    int number_count;
    scanf("%d", &number_count);
    
    printf("Enter numbers separated by space: ");
    int number, sum = 0, product = 1;
    for (int i = 0; i < number_count; i++)
    {
        scanf("%d", &number);
        sum += number;
        product *= number;
    }
    
    printf("\nArithmetic mean: %.2f", (float)sum / number_count);
    printf("\nGeometric mean: %.2f", pow(product, 1./number_count));
}