#include "InputHandler.h"
#include "Matrix.h"

constexpr auto PRINT_PRECISION = 2;

vector<vector<double>> createMatrix(const size_t&, const size_t&);
vector<vector<double>> createVector(const size_t&);

int main(void)
{
	//cout << Vector{ 1, 3, 4 }.crossProduct(3, 2);
	Vector v;
	v = { 1, 2 };
	cout << v << "\n";

	//cout << Vector{ 1, 3, -5 };
	//cout << Vector{ 1, 3, -5 }.dotProduct(Vector{ 4, -2, -1 });

	//size_t rows = getInput<size_t>("Enter rows count: ");
	//size_t columns = getInput<size_t>("Enter columns count: ");
	//
	//auto matrix = createMatrix(rows, columns);
	//printMatrix(matrix, PRINT_PRECISION);

	//auto onesVector = createVector(columns);
	//printMatrix(onesVector, PRINT_PRECISION);

	//auto multipliedMatrixes = multiplyMatrixes(matrix, onesVector);
	//printMatrix(multipliedMatrixes, PRINT_PRECISION);
}

vector<vector<double>> createMatrix(const size_t& rows, const size_t& columns)
{
	auto matrix = vector<vector<double>>(rows, vector<double>(columns));
	for (size_t i = 0; i < rows; i++)
		for (size_t j = 0; j < columns; j++)
			matrix[i][j] = i == j ? 100 * (i + 1) : sqrt((i + 1) * (j + 1));
	return matrix;
}

vector<vector<double>> createVector(const size_t& rows)
{
	return vector<vector<double>>(rows, vector<double>(1, 1));
}