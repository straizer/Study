#include "matrix.h"
#include "array.h"

#include <math.h>

Array findOddRowOddColumnElements(Matrix);

int main(void)
{
    size_t W, K;
    printf("Enter W (row count): ");
    scanf("%ld", &W);
    printf("Enter K (column count): ");
    scanf("%ld", &K);
    
    Matrix matrix = getInputMatrix(W, K);
    Array wynik = findOddRowOddColumnElements(matrix);
    
    printf("\nElements with odd row and odd column: ");
    printArray(wynik);
    
    deleteMatrix(matrix);
    deleteArray(wynik);
}

Array findOddRowOddColumnElements(Matrix matrix)
{
    int (*matrix_items)[matrix.column] = (void*)matrix.items;
    
    Array wynik = createArray(ceil(matrix.row / 2.) * ceil(matrix.column / 2.));
    size_t wynik_counter = 0;
    
    for (size_t j = 0; j < matrix.column; j++)
        for (size_t i = 0; i < matrix.row; i++)
            if (i % 2 == 0 && j % 2 == 0)
            {
                wynik.items[wynik_counter] = matrix_items[i][j];
                wynik_counter++;
            }
    
    return wynik;
}