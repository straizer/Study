#pragma once

#include <iostream>
#include <vector>

using std::vector;
using std::ostream;
using std::istream;
using std::initializer_list;

/// <summary>
/// Class representing mathematical vector of real numbers.
/// </summary>
class Vector
{
private:

	/// <summary>
	/// Private field that holds elements of Vector.
	/// </summary>
	vector<double> value;

	/// <summary>
	/// Gives maximum number of digits before decimal point for elements in given Vector.
	/// </summary>
	/// <param name="_vector">- Vector to check</param>
	/// <returns>Maximum number of digits before decimal point.</returns>
	static size_t getMaxDigitsCount(const Vector& _vector) noexcept;

	/// <summary>
	/// Writes Vector separator to given stream.
	/// </summary>
	/// <param name="stream">- output stream</param>
	/// <param name="length">- Vector length</param>
	/// <param name="width">- width of each element</param>
	/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
	static void writeSeparators(ostream& stream, const size_t& length, const size_t& width);

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


	/// <summary>
	/// Gives Vector element with given index.
	/// </summary>
	/// <param name="index">- index of element to retrieve</param>
	/// <exception cref="std::out_of_range">If given index is greater than index of last element.</exception>
	/// <returns>Vector element with given index.</returns>
	double& at(const size_t& index);

	/// <summary>
	/// Gives Vector element with given index.
	/// </summary>
	/// <param name="index">- index of element to retrieve</param>
	/// <exception cref="std::out_of_range">If given index is greater than index of last element.</exception>
	/// <returns>Vector element with given index.</returns>
	const double& at(const size_t& index) const;


	/// <summary>
	/// Multiplies two given Vectors (dot product).
	/// </summary>
	/// <param name="left">- factor Vector</param>
	/// <param name="right">- factor Vector</param>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>Dot product value.</returns>
	static double dotProduct(const Vector& left, const Vector& right);

	/// <summary>
	/// Multiplies current Vector by given Vector (dot product).
	/// </summary>
	/// <param name="other">- factor Vector</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Dot product value.</returns>
	double dotProduct(const Vector& other) const;

	/// <summary>
	/// Multiplies given Vector by given list of numbers (dot product).
	/// </summary>
	/// <param name="_vector">- factor Vector</param>
	/// <param name="list">- factor list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Dot product value.</returns>
	static double dotProduct(const Vector& _vector, const initializer_list<double>& list);

	/// <summary>
	/// Multiplies current Vector by given list of numbers (dot product).
	/// </summary>
	/// <param name="list">- factor list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Dot product value.</returns>
	double dotProduct(const initializer_list<double>& list) const;


	/// <summary>
	/// Multiplies two given Vectors (cross product).
	/// </summary>
	/// <param name="left">- factor Vector</param>
	/// <param name="right">- factor Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has length different than 3.</exception>
	/// <returns>New cross product Vector.</returns>
	static Vector crossProduct(const Vector& left, const Vector& right);

	/// <summary>
	/// Multiplies current Vector by given Vector (cross product).
	/// </summary>
	/// <param name="other">- factor Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If current Vector or given Vector has length different than 3.</exception>
	/// <returns>New cross product Vector.</returns>
	Vector crossProduct(const Vector& other) const;

	/// <summary>
	/// Multiplies given Vector by given list of numbers (cross product).
	/// </summary>
	/// <param name="_vector">- factor Vector</param>
	/// <param name="list">- factor list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If current Vector or given Vector has length different than 3.</exception>
	/// <returns>New cross product Vector.</returns>
	static Vector crossProduct(const Vector& _vector, const initializer_list<double>& list);

	/// <summary>
	/// Multiplies current Vector by given list of numbers (cross product).
	/// </summary>
	/// <param name="list">- factor list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If current Vector or given list has length different than 3.</exception>
	/// <returns>New cross product Vector.</returns>
	Vector crossProduct(const initializer_list<double>& list) const;


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
	/// <param name="other">- addend Vector</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator+=(const Vector& other);

	/// <summary>
	/// Adds two given Vectors.
	/// </summary>
	/// <param name="left">- addend Vector</param>
	/// <param name="right">- addend Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after addition.</returns>
	friend Vector operator+(Vector left, const Vector& right);

	/// <summary>
	/// Adds given number to all current Vector elements.
	/// </summary>
	/// <param name="number">- addend number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator+=(const double& number);

	/// <summary>
	/// Adds given number to all given Vector elements.
	/// </summary>
	/// <param name="_vector">- addend Vector</param>
	/// <param name="number">- addend number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after addition.</returns>
	friend Vector operator+(Vector _vector, const double& number);

	/// <summary>
	/// Adds given list of numbers to current Vector.
	/// </summary>
	/// <param name="list">- addend list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator+=(const initializer_list<double>& list);


	/// <summary>
	/// Substracts given Vector from current Vector.
	/// </summary>
	/// <param name="other">- subtrahend Vector</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after substraction.</returns>
	Vector& operator-=(const Vector& other);

	/// <summary>
	/// Substracts two given Vectors.
	/// </summary>
	/// <param name="left">- minuend Vector</param>
	/// <param name="right">- subtrahend Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after substraction.</returns>
	friend Vector operator-(Vector left, const Vector& right);

	/// <summary>
	/// Substracts given number from all current Vector elements.
	/// </summary>
	/// <param name="number">- subtrahend number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after substraction.</returns>
	Vector& operator-=(const double& number);

	/// <summary>
	/// Substracts given number from all given Vector elements.
	/// </summary>
	/// <param name="_vector">- minuend Vector</param>
	/// <param name="number">- subtrahend number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after substraction.</returns>
	friend Vector operator-(Vector _vector, const double& number);

	/// <summary>
	/// Substracts given list of numbers from current Vector.
	/// </summary>
	/// <param name="list">- subtrahend list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Current Vector after substraction.</returns>
	Vector& operator-=(const initializer_list<double>& list);


	/// <summary>
	/// Multiplies current Vector by given Vector (element by element).
	/// </summary>
	/// <param name="other">- factor Vector</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after multiplication.</returns>
	Vector& operator*=(const Vector& other);

	/// <summary>
	/// Multiplies two given Vectors (element by element).
	/// </summary>
	/// <param name="left">- factor Vector</param>
	/// <param name="right">- factor Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after multiplication.</returns>
	friend Vector operator*(Vector left, const Vector& right);

	/// <summary>
	/// Multiplies all current Vector elements by given number.
	/// </summary>
	/// <param name="number">- factor number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after multiplication.</returns>
	Vector& operator*=(const double& number);

	/// <summary>
	/// Multiplies all given Vector elements by given number.
	/// </summary>
	/// <param name="_vector">- factor Vector</param>
	/// <param name="number">- factor number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after multiplication.</returns>
	friend Vector operator*(Vector _vector, const double& number);

	/// <summary>
	/// Multiplies current Vector by given list of numbers (element by element).
	/// </summary>
	/// <param name="list">- factor list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Current Vector after multiplication.</returns>
	Vector& operator*=(const initializer_list<double>& list);


	/// <summary>
	/// Divides current Vector by given Vector (element by element).
	/// </summary>
	/// <param name="other">- divisor Vector</param>
	/// <exception cref="std::overflow_error">If any element in divisor Vector is zero.</exception>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after division.</returns>
	Vector& operator/=(const Vector& other);

	/// <summary>
	/// Divides two given Vectors (element by element).
	/// </summary>
	/// <param name="left">- dividend Vector</param>
	/// <param name="right">- divisor Vector</param>
	/// <exception cref="std::overflow_error">If any element in divisor Vector is zero.</exception>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after division.</returns>
	friend Vector operator/(Vector left, const Vector& right);

	/// <summary>
	/// Divides all current Vector elements by given number.
	/// </summary>
	/// <param name="number">- divisor number</param>
	/// <exception cref="std::overflow_error">If divisor number is zero.</exception>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after division.</returns>
	Vector& operator/=(const double& number);

	/// <summary>
	/// Divides all given Vector elements by given number.
	/// </summary>
	/// <param name="_vector">- dividend Vector</param>
	/// <param name="number">- divisor number</param>
	/// <exception cref="std::overflow_error">If divisor number is zero.</exception>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after division.</returns>
	friend Vector operator/(Vector _vector, const double& number);

	/// <summary>
	/// Divides current Vector by given list of numbers (element by element).
	/// </summary>
	/// <param name="list">- divisor list of numbers</param>
	/// <exception cref="std::overflow_error">If any element in divisor list of numbers is zero.</exception>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Current Vector after division.</returns>
	Vector& operator/=(const initializer_list<double>& list);


	/// <summary>
	/// Raises current Vector to power of given Vector (element by element).
	/// </summary>
	/// <param name="other">- exponent Vector</param>
	/// <exception cref="std::length_error">If given Vector has different length than current Vector.</exception>
	/// <returns>Current Vector after exponentiation.</returns>
	Vector& operator^=(const Vector& other);

	/// <summary>
	/// Raises two given Vectors to power (element by element).
	/// </summary>
	/// <param name="left">- base Vector</param>
	/// <param name="right">- exponent Vector</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given Vectors has different length.</exception>
	/// <returns>New Vector after exponentiation.</returns>
	friend Vector operator^(Vector left, const Vector& right);

	/// <summary>
	/// Raises all current Vector elements to power of given number.
	/// </summary>
	/// <param name="number">- exponent number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after exponentiation.</returns>
	Vector& operator^=(const double& number);

	/// <summary>
	/// Raises all given Vector elements to power of given number.
	/// </summary>
	/// <param name="_vector">- base Vector</param>
	/// <param name="number">- exponent number</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New Vector after exponentiation.</returns>
	friend Vector operator^(Vector _vector, const double& number);

	/// <summary>
	/// Raises current Vector to power of given list of numbers (element by element).
	/// </summary>
	/// <param name="list">- exponent list of numbers</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If given list has different length than current Vector.</exception>
	/// <returns>Current Vector after exponentiation.</returns>
	Vector& operator^=(const initializer_list<double>& list);


	/// <summary>
	/// Adds one to all current Vector elements.
	/// </summary>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after addition.</returns>
	Vector& operator++(void);

	/// <summary>
	/// Adds one to all current Vector elements.
	/// </summary>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector before addition.</returns>
	Vector operator++(int);

	/// <summary>
	/// Substracts one from all current Vector elements.
	/// </summary>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector after substraction.</returns>
	Vector& operator--(void);

	/// <summary>
	/// Substracts one from all current Vector elements.
	/// </summary>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>Current Vector before substraction.</returns>
	Vector operator--(int);


	/// <summary>
	/// Negates all elements in current Vector.
	/// </summary>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <returns>New negated Vector.</returns>
	Vector operator-(void) const;


	/// <summary>
	/// Compares two given Vectors.
	/// </summary>
	/// <param name="left">- Vector to compare</param>
	/// <param name="right">- Vector to compare</param>
	/// <returns>true if given Vectors have the same length and consecutive elements are equal; false otherwise.</returns>
	friend bool operator==(const Vector& left, const Vector& right) noexcept;

	/// <summary>
	/// Compares two given Vectors.
	/// </summary>
	/// <param name="left">- Vector to compare</param>
	/// <param name="right">- Vector to compare</param>
	/// <returns>true if given Vectors have different length or consecutive elements are not equal; false otherwise.</returns>
	friend bool operator!=(const Vector& left, const Vector& right) noexcept;


	/// <summary>
	/// Writes Vector to given stream.
	/// </summary>
	/// <param name="stream">- output stream</param>
	/// <param name="_vector">- Vector to write</param>
	/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
	/// <returns>Output stream after writing Vector.</returns>
	friend ostream& operator<<(ostream& stream, const Vector& _vector);

	/// <summary>
	/// Reads Vector from given stream.
	/// </summary>
	/// <param name="stream">- input stream</param>
	/// <param name="_vector">- Vector into which input will be written</param>
	/// <exception cref="std::bad_alloc">If allocation fails.</exception>
	/// <exception cref="std::length_error">If total size of Vector is too big.</exception>
	/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
	/// <returns>Input stream after reading Vector.</returns>
	friend istream& operator>>(istream& stream, Vector& _vector);


	/// <summary>
	/// Casts current Vector to bool.
	/// </summary>
	/// <returns>true if current Vector is not empty; false otherwise.</returns>
	operator bool(void) const noexcept;
};