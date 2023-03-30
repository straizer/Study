#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void)
{
    size_t limit;
    printf("Enter upper limit (integer >2): ");
    scanf("%ld", &limit);
    
    int* b_isComplex = calloc(limit + 1, (limit + 1) * sizeof(int));
    
    for (size_t i = 2; i <= sqrt(limit); i++)
        if (!b_isComplex[i])
            for (size_t j = i * 2; j <= limit; j += i)
                b_isComplex[j] = 1;
               
    printf("Prime numbers:\n"); 
    for (size_t i = 2; i <= limit; i++)
        if (!b_isComplex[i])
            printf("%ld ", i);
    
    free(b_isComplex);
}