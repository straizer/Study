#include "matrix.h"

Matrix multiplyMatrixByMatrix(Matrix, Matrix);

int main(void)
{
    size_t W, K;
    printf("Enter W (row count in matrix A and column count in matrix B): ");
    scanf("%ld", &W);
    printf("Enter K (column count in matrix A and row count in matrix B): ");
    scanf("%ld", &K);
    
    printf("\nMATRIX A");
    Matrix matrix_A = getInputMatrix(W, K);
    
    printf("\nMATRIX B");
    Matrix matrix_B = getInputMatrix(K, W);

    Matrix multiplied_matrix = multiplyMatrixByMatrix(matrix_A, matrix_B);
    
    printf("\nMultiplied matrix:\n");
    printMatrix(multiplied_matrix);
    
    deleteMatrix(matrix_A);
    deleteMatrix(matrix_B);
    deleteMatrix(multiplied_matrix);
}

Matrix multiplyMatrixByMatrix(Matrix matrix_A, Matrix matrix_B)
{
    Matrix multiplied_matrix = createMatrix(matrix_A.row, matrix_B.column);
    
    double (*items_A)[matrix_A.column] = (void*)matrix_A.items;
    double (*items_B)[matrix_B.column] = (void*)matrix_B.items;
    double (*multiplied_items)[multiplied_matrix.column] = (void*)multiplied_matrix.items;
    
    for (size_t i = 0; i < multiplied_matrix.row; i++)
        for (size_t j = 0; j < multiplied_matrix.column; j++)
            for (size_t k = 0; k < matrix_A.column; k++)
                multiplied_items[i][j] += items_A[i][k] * items_B[k][j];
    
    return multiplied_matrix;
}