#include "matrix.h"

Matrix createMatrix(size_t row, size_t column)
{
    Matrix matrix;
    matrix.row = row;
    matrix.column = column;
    matrix.items = calloc(row * column, row * column * sizeof(double));
    return matrix;
}

Matrix getInputMatrix(size_t row, size_t column)
{
    Matrix matrix = createMatrix(row, column);
    double (*items)[column] = (void*)matrix.items;
    
    printf("\nIn each row enter %ld numbers separated by spaces.\n", column);
    for (size_t i = 0; i < row; i++)
    {
        printf("Enter %ld row: ", i + 1);
        for (size_t j = 0; j < column; j++)
            scanf("%lf", &items[i][j]);
        while (getchar() != '\n');
    }
    
    return matrix;
}

void printMatrix(Matrix matrix)
{
    double (*items)[matrix.column] = (void*)matrix.items;
    for (size_t i = 0; i < matrix.row; i++)
    {
        for (size_t j = 0; j < matrix.column; j++)
            printf("%9.2lf ", items[i][j]);
        printf("\n\n");
    }
}

void deleteMatrix(Matrix matrix)
{
    free(matrix.items);
}