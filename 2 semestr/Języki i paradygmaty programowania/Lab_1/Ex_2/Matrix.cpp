#include "Matrix.h"

//#include <iomanip>
//
//using std::cout;
//using std::fixed;
//using std::right;
//using std::setprecision;
//using std::setw;
using std::length_error;
//
//static void printSeparators(const size_t& columns, const size_t& width)
//{
//	cout << "\n|";
//	for (size_t i = 0; i < columns; i++)
//	{
//		for (size_t j = 0; j < width; j++)
//			cout << "-";
//		cout << "|";
//	}
//	cout << "\n";
//}
//
//vector<vector<double>> transposeMatrix(const vector<vector<double>>& matrix)
//{
//	auto result = vector<vector<double>>(matrix[0].size(), vector<double>(matrix.size()));
//	for (size_t i = 0; i < matrix.size(); i++)
//		for (size_t j = 0; j < matrix[0].size(); j++)
//			result[j][i] = matrix[i][j];
//	return result;
//}
//
//vector<vector<double>> multiplyMatrixes(const vector<vector<double>>& matrix1, const vector<vector<double>>& matrix2)
//{
//	if (matrix1[0].size() != matrix2.size())
//		throw length_error("Columns count in first matrix should be the same as rows count in second matrix.");
//
//	auto transposedMatrix2 = transposeMatrix(matrix2);
//	auto result = vector<vector<double>>(matrix1.size(), vector<double>(matrix2[0].size()));
//	for (size_t i = 0; i < result.size(); i++)
//		for (size_t j = 0; j < result[0].size(); j++)
//			result[i][j] = dotProduct(matrix1[i], transposedMatrix2[j]);
//	return result;
//}
//
//void printMatrix(const vector<vector<double>>& matrix, const size_t& precision)
//{
//	cout << setprecision(precision) << fixed << right;
//	printSeparators(matrix[0].size(), precision + 5);
//	for (size_t i = 0; i < matrix.size(); i++)
//	{
//		cout << "|";
//		for (size_t j = 0; j < matrix[0].size(); j++)
//			cout << setw(precision + 5) << matrix[i][j] << "|";
//		printSeparators(matrix[0].size(), precision + 5);
//	}
//}

Matrix::Matrix(void) : value(vector<Vector>()) { }

Matrix::Matrix(const size_t& rows, const size_t& columns) : value(vector<Vector>(rows, Vector(columns))) { }

Matrix::Matrix(const size_t& rows, const size_t& columns, const double& defaultValue) : value(vector<Vector>(rows, Vector(columns, defaultValue))) { }

Matrix::Matrix(const initializer_list<initializer_list<double>>& list) : value(vector<Vector>(list.size()))
{ 
	size_t columns = list.begin()->size();

	for (size_t i = 0; i < list.size(); i++)
	{
		auto sublist = *(list.begin() + i);

		if (sublist.size() != columns)
			throw length_error("Columns must have the same size.");

		value[i] = Vector(sublist);
	}
}


size_t Matrix::size(void) const
{
	return value.size();
}
