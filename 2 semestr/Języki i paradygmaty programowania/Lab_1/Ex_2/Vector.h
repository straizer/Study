#pragma once

#include <iostream>
#include <vector>

using std::vector;
using std::ostream;
using std::istream;
using std::initializer_list;

class Vector
{
private:
	vector<double> value;

public:
	explicit Vector(void);
	explicit Vector(const size_t&);
	explicit Vector(const size_t&, const double&);
	Vector(const initializer_list<double>&);

	size_t size(void) const;

	static double dotProduct(const Vector&, const Vector&);
	double dotProduct(const Vector&) const;
	static double dotProduct(const Vector&, const initializer_list<double>&);
	double dotProduct(const initializer_list<double>&) const;

	static Vector crossProduct(const Vector&, const Vector&);
	Vector crossProduct(const Vector&) const;
	static Vector crossProduct(const Vector&, const initializer_list<double>&);
	Vector crossProduct(const initializer_list<double>&) const;

	double& operator[](size_t);
	const double& operator[](size_t) const;

	Vector& operator+=(const Vector&);
	friend Vector operator+(Vector, const Vector&);
	Vector& operator+=(const double&);
	friend Vector operator+(Vector, const double&);
	Vector& operator+=(const initializer_list<double>&);

	Vector& operator-=(const Vector&);
	friend Vector operator-(Vector, const Vector&);
	Vector& operator-=(const double&);
	friend Vector operator-(Vector, const double&);
	Vector& operator-=(const initializer_list<double>&);

	Vector& operator*=(const Vector&);
	friend Vector operator*(Vector, const Vector&);
	Vector& operator*=(const double&);
	friend Vector operator*(Vector, const double&);
	Vector& operator*=(const initializer_list<double>&);

	Vector& operator/=(const Vector&);
	friend Vector operator/(Vector, const Vector&);
	Vector& operator/=(const double&);
	friend Vector operator/(Vector, const double&);
	Vector& operator/=(const initializer_list<double>&);

	Vector& operator^=(const Vector&);
	friend Vector operator^(Vector, const Vector&);
	Vector& operator^=(const double&);
	friend Vector operator^(Vector, const double&);
	Vector& operator^=(const initializer_list<double>&);

	Vector& operator++();
	Vector operator++(int);
	Vector& operator--();
	Vector operator--(int);

	friend bool operator==(const Vector&, const Vector&);
	friend bool operator!=(const Vector&, const Vector&);

	friend ostream& operator<<(ostream&, const Vector&);
	friend istream& operator>>(istream&, Vector&);

	operator bool() const;
};