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
	/// <summary>
	/// Constructs empty Vector.
	/// </summary>
	explicit Vector(void) noexcept;

	/// <summary>
	/// Constructs Vector of given size filled with zeros.
	/// </summary>
	/// <param name="size">- size of Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If total size of Vector is too big.</exception>
	explicit Vector(const size_t& size);

	/// <summary>
	/// Constructs Vector of given size filled with given value.
	/// </summary>
	/// <param name="size">- size of Vector</param>
	/// <param name="value">- default value to fill Vector with</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If total size of Vector is too big.</exception>
	explicit Vector(const size_t& size, const double& value);

	/// <summary>
	/// Constructs Vector with values set to given list.
	/// </summary>
	/// <param name="list">- list of values to fill Vector with</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If total size of list is too big to create Vector.</exception>
	Vector(const initializer_list<double>& list);


	/// <summary>
	/// Gives Vector length.
	/// </summary>
	/// <returns>Vector length.</returns>
	size_t size(void) const noexcept;


	static double dotProduct(const Vector& a, const Vector&);
	double dotProduct(const Vector&) const;
	static double dotProduct(const Vector&, const initializer_list<double>&);
	double dotProduct(const initializer_list<double>&) const;

	static Vector crossProduct(const Vector&, const Vector&);
	Vector crossProduct(const Vector&) const;
	static Vector crossProduct(const Vector&, const initializer_list<double>&);
	Vector crossProduct(const initializer_list<double>&) const;


	/// <summary>
	/// Gives Vector element with given index.
	/// </summary>
	/// <param name="index">- index of element to retrieve</param>
	/// <exception cref="std::out_of_range">If given index is greater than index of last element.</exception>
	/// <returns>Vector element with given index.</returns>
	double& operator[](const size_t& index);

	/// <summary>
	/// Gives Vector element with given index.
	/// </summary>
	/// <param name="index">- index of element to retrieve</param>
	/// <exception cref="std::out_of_range">If given index is greater than index of last element.</exception>
	/// <returns>Vector element with given index.</returns>
	const double& operator[](const size_t& index) const;


	/// <summary>
	/// Adds given Vector to current Vector.
	/// </summary>
	/// <param name="other">- Vector to add</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator+=(const Vector& other);

	/// <summary>
	/// Adds two given Vectors.
	/// </summary>
	/// <param name="left">- Vector to add</param>
	/// <param name="right">- Vector to add</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after addition.</returns>
	friend Vector operator+(Vector left, const Vector& right);

	/// <summary>
	/// Adds given number to all current Vector elements.
	/// </summary>
	/// <param name="number">- number to add</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator+=(const double& number);

	/// <summary>
	/// Adds given number to all given Vector elements.
	/// </summary>
	/// <param name="_vector">- Vector to add</param>
	/// <param name="number">- number to add</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after addition.</returns>
	friend Vector operator+(Vector _vector, const double& number);

	/// <summary>
	/// Adds given list of numbers to current Vector.
	/// </summary>
	/// <param name="list">- list of numbers to add</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector or total size of list is too big to create Vector.</exception>
	/// <returns></returns>
	Vector& operator+=(const initializer_list<double>& list);

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