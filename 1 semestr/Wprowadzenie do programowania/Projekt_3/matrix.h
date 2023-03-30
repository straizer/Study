#include <stdio.h>

typedef struct {
    int* items;
    size_t row;
    size_t column;
} Matrix;

Matrix createMatrix(size_t, size_t);
Matrix getInputMatrix(size_t, size_t);
void deleteMatrix(Matrix);
