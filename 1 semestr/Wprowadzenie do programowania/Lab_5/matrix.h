#include <stdio.h>
#include <stdlib.h>

typedef struct {
    double* items;
    size_t row;
    size_t column;
} Matrix;

Matrix createMatrix(size_t, size_t);
Matrix getInputMatrix(size_t, size_t);
void printMatrix(Matrix);
void deleteMatrix(Matrix);
