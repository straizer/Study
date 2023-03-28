#include "double_matrix.h"

DoubleMatrix createDoubleMatrix(size_t row, size_t column)
{
    DoubleMatrix matrix;
    matrix.row = row;
    matrix.column = column;
    matrix.items = calloc(row * column, row * column * sizeof(double));
    return matrix;
}

// double (*items)[matrix.column] = (void*)matrix.items;

void deleteDoubleMatrix(DoubleMatrix matrix)
{
    free(matrix.items);
}