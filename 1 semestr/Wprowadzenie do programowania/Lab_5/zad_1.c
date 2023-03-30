#include "matrix.h"

double calculateComplement(size_t, size_t, Matrix);
double calculateDeterminant(Matrix);
Matrix calculateComplementMatrix(Matrix);
Matrix calculateTransposeMatrix(Matrix);
Matrix multiplyMatrixByNumber(double, Matrix);

int main(void)
{
    size_t size;
    printf("Enter matrix size: ");
    scanf("%ld", &size);
    
    Matrix matrix = getInputMatrix(size, size);
    double determinant = calculateDeterminant(matrix);
    
    if (determinant == 0)
    {
        printf("\nMatrix determinant is equal 0 -> no inverse matrix.\n");
        deleteMatrix(matrix);
        return 0;
    }
    
    Matrix complement_matrix = calculateComplementMatrix(matrix);
    Matrix transpose_matrix = calculateTransposeMatrix(complement_matrix);
    Matrix inverse_matrix = multiplyMatrixByNumber(1 / determinant, transpose_matrix);
    
    printf("\nInverse matrix:\n");
    printMatrix(inverse_matrix);
    
    deleteMatrix(matrix);
    deleteMatrix(complement_matrix);
    deleteMatrix(transpose_matrix);
    deleteMatrix(inverse_matrix);
}

double calculateComplement(size_t row, size_t column, Matrix matrix)
{
    double (*items)[matrix.column] = (void*)matrix.items;
    
    if (matrix.row == 1)
        return 1;
    
    Matrix minor_matrix = createMatrix(matrix.row - 1, matrix.column - 1);
    size_t minor_matrix_counter = 0;
    
    for (size_t i = 0; i < matrix.row; i++)
        for (size_t j = 0; j < matrix.column; j++)
            if (i != row && j != column)
            {
                minor_matrix.items[minor_matrix_counter] = items[i][j];
                minor_matrix_counter++;
            }
            
    double complement = ((row + column) % 2 == 0 ? 1 : -1) * calculateDeterminant(minor_matrix);
    deleteMatrix(minor_matrix);
    
    return complement;
}

double calculateDeterminant(Matrix matrix)
{
    double (*items)[matrix.column] = (void*)matrix.items;
    
    if (matrix.row == 1)
        return items[0][0];
    
    double sum = 0;
    for (size_t i = 0; i < matrix.column; i++)
        sum += items[0][i] * calculateComplement(0, i, matrix); 
        
    return sum;
}

Matrix calculateComplementMatrix(Matrix matrix)
{
    Matrix complement_matrix = createMatrix(matrix.row, matrix.column);
    
    double (*items)[matrix.column] = (void*)matrix.items;
    double (*complement_items)[complement_matrix.column] = (void*)complement_matrix.items;
    
    for (size_t i = 0; i < matrix.row; i++)
        for (size_t j = 0; j < matrix.column; j++)
            complement_items[i][j] = calculateComplement(i, j, matrix);
    
    return complement_matrix;
}

Matrix calculateTransposeMatrix(Matrix matrix)
{
    Matrix transpose_matrix = createMatrix(matrix.row, matrix.column);
    
    double (*items)[matrix.column] = (void*)matrix.items;
    double (*transpose_items)[transpose_matrix.column] = (void*)transpose_matrix.items;
    
    for (size_t i = 0; i < matrix.row; i++)
        for (size_t j = 0; j < matrix.column; j++)
            transpose_items[j][i] = items[i][j];
    
    return transpose_matrix;
}

Matrix multiplyMatrixByNumber(double number, Matrix matrix)
{
    Matrix multiplied_matrix = createMatrix(matrix.row, matrix.column);
    
    double (*items)[matrix.column] = (void*)matrix.items;
    double (*multiplied_items)[multiplied_matrix.column] = (void*)multiplied_matrix.items;
    
    for (size_t i = 0; i < matrix.row; i++)
        for (size_t j = 0; j < matrix.column; j++)
            multiplied_items[i][j] = number * items[i][j];
    
    return multiplied_matrix;
}