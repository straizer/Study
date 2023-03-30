#include <stdio.h>
#include <math.h>

int main(void)
{
    printf("GEOMETRIC MEAN OF EVEN TERMS\nEnter last acceptable value: ");
    int last;
    scanf("%d", &last);
    
    printf("Enter numbers: ");
    
    int value, product = 1, even_count = 0;
    do
    {
        scanf("%d", &value);
        if (value % 2 == 1)
            continue;
        product *= value;
        even_count++;
        
    } while (value != last);
    
    printf("\nResult: %.2f", pow(product, 1./even_count));
}