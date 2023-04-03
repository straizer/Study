#include "Vector.h"

#include <stdexcept>
#include <iomanip>
#include <format>
#include "InputHandler.h"

using std::right;
using std::fixed;
using std::defaultfloat;
using std::setw;
using std::format;
using std::length_error;
using std::overflow_error;

static size_t _getMaxDigitsCount(const Vector&);
static void _printSeparators(ostream&, const size_t&, const size_t&);


Vector::Vector(const size_t& size, const double& defaultValue) : value(vector<double>(size, defaultValue)) { }

Vector::Vector(const size_t& size) : value(vector<double>(size, 0)) { }

Vector::Vector(const initializer_list<double>& list) : value(list) { }


size_t Vector::size(void) const
{
	return value.size();
}


double Vector::dotProduct(const Vector& lhs, const Vector& rhs)
{
	if (lhs.size() != rhs.size())
		throw length_error("Size of vectors are not the same.");

	double result = 0;
	for (size_t i = 0; i < lhs.size(); i++)
		result += lhs[i] * rhs[i];

	return result;
}

double Vector::dotProduct(const Vector& other) const
{
	return Vector::dotProduct(*this, other);
}

double Vector::dotProduct(const Vector& lhs, const initializer_list<double>& list)
{
	return Vector::dotProduct(lhs, Vector(list));
}

double Vector::dotProduct(const initializer_list<double>& list) const
{
	return Vector::dotProduct(*this, list);
}


Vector Vector::crossProduct(const Vector& lhs, const Vector& rhs)
{
	if (lhs.size() != rhs.size())
		throw length_error("Size of vectors are not the same.");

	if (lhs.size() == 7)
		throw length_error("Cross product of 7D vectors not implemented yet.");

	if (lhs.size() != 3)
		throw length_error("Cross product of 2 vectors exist only for 3D and 7D vectors.");

	Vector result = Vector(3);
	for (size_t i = 0; i < 3; i++)
		result[i] = lhs[(i + 1) % 3] * rhs[(i + 2) % 3] - lhs[(i + 2) % 3] * rhs[(i + 1) % 3];

	return result;
}

Vector Vector::crossProduct(const Vector& other) const
{
	return Vector::crossProduct(*this, other);
}

Vector Vector::crossProduct(const Vector& lhs, const initializer_list<double>& list)
{
	return Vector::crossProduct(lhs, Vector(list));
}

Vector Vector::crossProduct(const initializer_list<double>& list) const
{
	return Vector::crossProduct(*this, list);
}


double& Vector::operator[](size_t id)
{
	return value[id];
}

const double& Vector::operator[](size_t id) const
{
	return value[id];
}


Vector& Vector::operator+=(const Vector& other)
{
	if (value.size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < value.size(); i++)
		value[i] += other[i];

	return *this;
}

Vector operator+(Vector lhs, const Vector& rhs)
{	
	return lhs += rhs;
}

Vector& Vector::operator+=(const double& number)
{
	return *this += Vector(this->size(), number);
}

Vector operator+(Vector lhs, const double& number)
{	
	return lhs += number;
}

Vector& Vector::operator+=(const initializer_list<double>& list)
{
	return *this += Vector(list);
}



Vector& Vector::operator-=(const Vector& other)
{
	if (value.size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < value.size(); i++)
		value[i] -= other[i];

	return *this;
}

Vector operator-(Vector lhs, const Vector& rhs)
{	
	return lhs -= rhs;
}

Vector& Vector::operator-=(const double& number)
{
	return *this -= Vector(this->size(), number);
}

Vector operator-(Vector lhs, const double& number)
{	
	return lhs -= number;
}

Vector& Vector::operator-=(const initializer_list<double>& list)
{
	return *this -= Vector(list);
}


Vector& Vector::operator*=(const Vector& other)
{
	if (value.size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < value.size(); i++)
		value[i] *= other[i];

	return *this;
}

Vector operator*(Vector lhs, const Vector& rhs)
{
	return lhs *= rhs;
}

Vector& Vector::operator*=(const double& number)
{
	return *this *= Vector(this->size(), number);
}

Vector operator*(Vector lhs, const double& number)
{
	return lhs *= number;
}

Vector& Vector::operator*=(const initializer_list<double>& list)
{
	return *this *= Vector(list);
}


Vector& Vector::operator/=(const Vector& other)
{
	if (value.size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < value.size(); i++)
		if (other[i] == 0)
			throw overflow_error("Divide by zero exception.");
		else
			value[i] /= other[i];

	return *this;
}

Vector operator/(Vector lhs, const Vector& rhs)
{
	return lhs /= rhs;
}

Vector& Vector::operator/=(const double& number)
{
	return *this /= Vector(this->size(), number);
}

Vector operator/(Vector lhs, const double& number)
{
	return lhs /= number;
}

Vector& Vector::operator/=(const initializer_list<double>& list)
{
	return *this /= Vector(list);
}


Vector& Vector::operator^=(const Vector& other)
{
	if (value.size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < value.size(); i++)
		value[i] = pow(value[i], other[i]);

	return *this;
}

Vector operator^(Vector lhs, const Vector& rhs)
{
	return lhs ^= rhs;
}

Vector& Vector::operator^=(const double& number)
{
	return *this ^= Vector(this->size(), number);
}

Vector operator^(Vector lhs, const double& number)
{
	return lhs ^= number;
}

Vector& Vector::operator^=(const initializer_list<double>& list)
{
	return *this ^= Vector(list);
}


Vector& Vector::operator++()
{
	return *this += 1;
}

Vector Vector::operator++(int)
{
	Vector old = *this;
	++*this;
	return old;
}

Vector& Vector::operator--()
{
	return *this -= 1;
}

Vector Vector::operator--(int)
{
	Vector old = *this;
	--*this;
	return old;
}


bool operator==(const Vector& lhs, const Vector& rhs)
{
	if (lhs.size() != rhs.size())
		return false;

	for (size_t i = 0; i < lhs.size(); i++)
		if (lhs[i] != rhs[i])
			return false;

	return true;
}

bool operator!=(const Vector& lhs, const Vector& rhs)
{
	return !(lhs == rhs);
}


ostream& operator<<(ostream& stream, const Vector& _vector)
{
	size_t width = stream.precision() + _getMaxDigitsCount(_vector) + 2;
	_printSeparators(stream, _vector.size(), width);	

	stream << fixed << "\n|";
	for (size_t i = 0; i < _vector.size(); i++)
		stream << setw(width) << _vector[i] << " |";

	_printSeparators(stream << "\n", _vector.size(), width);
	return stream << defaultfloat;
}

istream& operator>>(istream& stream, Vector& _vector)
{
	size_t size = getInput<size_t>("Enter vector size: ", stream);
	_vector = Vector(size);

	for (size_t i = 0; i < size; i++)
		_vector[i] = getInput<double>(format("Enter {}. element: ", i), stream);

	return stream;
}


static size_t _getMaxDigitsCount(const Vector& _vector)
{
	size_t maxDigitsCount = 0;
	for (size_t i = 0; i < _vector.size(); i++)
	{
#pragma warning (disable: 4244)
		size_t digitsCount = _vector[i] == 0 ? 1 : floor(log10(abs(_vector[i])) + (_vector[i] < 0 ? 2 : 1));
#pragma warning (default: 4244)
		if (digitsCount > maxDigitsCount)
			maxDigitsCount = digitsCount;
	}
	return maxDigitsCount;
}

static void _printSeparators(ostream& stream, const size_t& columns, const size_t& width)
{
	stream << "|";
	for (size_t i = 0; i < columns; i++)
	{
		for (size_t j = 0; j < width + 1; j++)
			stream << "-";
		stream << "|";
	}
}