#pragma once

#include "Vector.h"

//vector<vector<double>> transposeMatrix(const vector<vector<double>>&);
//vector<vector<double>> multiplyMatrixes(const vector<vector<double>>&, const vector<vector<double>>&);
//void printMatrix(const vector<vector<double>>&, const size_t&);

class Matrix
{
private:
	vector<Vector> value;

public:
	explicit Matrix(void);
	explicit Matrix(const size_t&, const size_t&);
	explicit Matrix(const size_t&, const size_t&, const double&);
	Matrix(const initializer_list<initializer_list<double>>&);

	size_t size(void) const;
	size_t size2D(void) const;
};