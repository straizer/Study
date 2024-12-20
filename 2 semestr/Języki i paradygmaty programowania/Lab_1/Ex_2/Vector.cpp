#include "Vector.h"

#include <stdexcept>
#include <iomanip>
#include <format>
#include "InputHandler.h"

using std::fixed;
using std::defaultfloat;
using std::setw;
using std::format;
using std::length_error;
using std::overflow_error;
using std::out_of_range;
using std::bad_alloc;

#pragma region Private methods

size_t Vector::getMaxDigitsCount(const Vector& _vector) noexcept
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

void Vector::writeSeparators(ostream& stream, const size_t& columns, const size_t& width)
{
	stream << "|";
	for (size_t i = 0; i < columns; i++)
	{
		for (size_t j = 0; j < width + 1; j++)
			stream << "-";
		stream << "|";
	}
}

#pragma endregion

#pragma region Constructors

Vector::Vector(void) noexcept : value(vector<double>()) { }

Vector::Vector(const size_t& size)
try : value(vector<double>(size, 0)) { }
catch (const length_error&)
{
	throw length_error("Vector length too big.");
}

Vector::Vector(const size_t& size, const double& value)
try : value(vector<double>(size, value)) { }
catch (const length_error&)
{
	throw length_error("Vector length too big.");
}

Vector::Vector(const initializer_list<double>& list)
try : value(list) { }
catch (const length_error&)
{
	throw length_error("List is too big to create Vector.");
}

#pragma endregion

#pragma region Public methods

size_t Vector::size(void) const noexcept
{
	return value.size();
}

double& Vector::at(const size_t& index)
{
	if (index >= size())
		throw out_of_range("Vector subscript out of range.");

	return value[index];
}

const double& Vector::at(const size_t& index) const
{
	if (index >= size())
		throw out_of_range("Vector subscript out of range.");

	return value[index];
}


double Vector::dotProduct(const Vector& left, const Vector& right)
{
	if (left.size() != right.size())
		throw length_error("Size of vectors are not the same.");

	double result = 0;
	for (size_t i = 0; i < left.size(); i++)
		result += left[i] * right[i];

	return result;
}

double Vector::dotProduct(const Vector& other) const
{
	return Vector::dotProduct(*this, other);
}

double Vector::dotProduct(const Vector& _vector, const initializer_list<double>& list)
{
	return Vector::dotProduct(_vector, Vector(list));
}

double Vector::dotProduct(const initializer_list<double>& list) const
{
	return Vector::dotProduct(*this, list);
}


Vector Vector::crossProduct(const Vector& left, const Vector& right)
{
	if (left.size() != right.size())
		throw length_error("Size of vectors are not the same.");

	if (left.size() == 7)
		throw length_error("Cross product of 7D vectors not implemented yet.");

	if (left.size() != 3)
		throw length_error("Cross product of 2 vectors exist only for 3D and 7D vectors.");

	Vector result = Vector(3);
	for (size_t i = 0; i < 3; i++)
		result[i] = left[(i + 1) % 3] * right[(i + 2) % 3] - left[(i + 2) % 3] * right[(i + 1) % 3];

	return result;
}

Vector Vector::crossProduct(const Vector& other) const
{
	return Vector::crossProduct(*this, other);
}

Vector Vector::crossProduct(const Vector& _vector, const initializer_list<double>& list)
{
	return Vector::crossProduct(_vector, Vector(list));
}

Vector Vector::crossProduct(const initializer_list<double>& list) const
{
	return Vector::crossProduct(*this, list);
}

#pragma endregion

#pragma region Operators

double& Vector::operator[](const size_t& index)
{
	return this->at(index);
}

const double& Vector::operator[](const size_t& index) const
{
	return this->at(index);
}


Vector& Vector::operator+=(const Vector& other)
{
	if (size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < size(); i++)
		this->at(i) += other[i];

	return *this;
}

Vector operator+(Vector left, const Vector& right)
{
	return left += right;
}

Vector& Vector::operator+=(const double& number)
{
	return *this += Vector(this->size(), number);
}

Vector operator+(Vector _vector, const double& number)
{
	return _vector += number;
}

Vector& Vector::operator+=(const initializer_list<double>& list)
{
	return *this += Vector(list);
}



Vector& Vector::operator-=(const Vector& other)
{
	if (size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < size(); i++)
		this->at(i) -= other[i];

	return *this;
}

Vector operator-(Vector left, const Vector& right)
{
	return left -= right;
}

Vector& Vector::operator-=(const double& number)
{
	return *this -= Vector(this->size(), number);
}

Vector operator-(Vector _vector, const double& number)
{
	return _vector -= number;
}

Vector& Vector::operator-=(const initializer_list<double>& list)
{
	return *this -= Vector(list);
}


Vector& Vector::operator*=(const Vector& other)
{
	if (size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < size(); i++)
		this->at(i) *= other[i];

	return *this;
}

Vector operator*(Vector left, const Vector& right)
{
	return left *= right;
}

Vector& Vector::operator*=(const double& number)
{
	return *this *= Vector(this->size(), number);
}

Vector operator*(Vector _vector, const double& number)
{
	return _vector *= number;
}

Vector& Vector::operator*=(const initializer_list<double>& list)
{
	return *this *= Vector(list);
}


Vector& Vector::operator/=(const Vector& other)
{
	if (size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < size(); i++)
		if (other[i] == 0)
			throw overflow_error("Divide by zero exception.");
		else
			this->at(i) /= other[i];

	return *this;
}

Vector operator/(Vector left, const Vector& right)
{
	return left /= right;
}

Vector& Vector::operator/=(const double& number)
{
	return *this /= Vector(this->size(), number);
}

Vector operator/(Vector _vector, const double& number)
{
	return _vector /= number;
}

Vector& Vector::operator/=(const initializer_list<double>& list)
{
	return *this /= Vector(list);
}


Vector& Vector::operator^=(const Vector& other)
{
	if (size() != other.size())
		throw length_error("Size of vectors are not the same.");

	for (size_t i = 0; i < size(); i++)
		this->at(i) = pow(this->at(i), other[i]);

	return *this;
}

Vector operator^(Vector left, const Vector& right)
{
	return left ^= right;
}

Vector& Vector::operator^=(const double& number)
{
	return *this ^= Vector(this->size(), number);
}

Vector operator^(Vector _vector, const double& number)
{
	return _vector ^= number;
}

Vector& Vector::operator^=(const initializer_list<double>& list)
{
	return *this ^= Vector(list);
}


Vector& Vector::operator++(void)
{
	return *this += 1;
}

Vector Vector::operator++(int)
{
	Vector old = *this;
	++*this;
	return old;
}

Vector& Vector::operator--(void)
{
	return *this -= 1;
}

Vector Vector::operator--(int)
{
	Vector old = *this;
	--*this;
	return old;
}


Vector Vector::operator-(void) const
{
	auto _vector = Vector(size());
	for (size_t i = 0; i < size(); i++)
		_vector[i] = -this->at(i);
	return _vector;
}


bool operator==(const Vector& left, const Vector& right) noexcept
{
	if (left.size() != right.size())
		return false;

	for (size_t i = 0; i < left.size(); i++)
		if (left[i] != right[i])
			return false;

	return true;
}

bool operator!=(const Vector& left, const Vector& right) noexcept
{
	return !(left == right);
}


ostream& operator<<(ostream& stream, const Vector& _vector)
{
	if (!_vector)
		return stream;

	size_t width = stream.precision() + Vector::getMaxDigitsCount(_vector) + 2;
	Vector::writeSeparators(stream, _vector.size(), width);

	stream << fixed << "\n|";
	for (size_t i = 0; i < _vector.size(); i++)
		stream << setw(width) << _vector[i] << " |";

	Vector::writeSeparators(stream << "\n", _vector.size(), width);
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


Vector::operator bool(void) const noexcept
{
	return !value.empty();
}

#pragma endregion