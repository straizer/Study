#pragma once

#include "Point.h"

class ComplexNumber : public Point
{
public:
	virtual ~ComplexNumber(void) = default;

	ComplexNumber(void);
	ComplexNumber(float x, float y);
	ComplexNumber(const Point& point);

	bool operator==(const ComplexNumber& other) const;
	bool operator!=(const ComplexNumber& other) const;
	ComplexNumber& operator+=(const ComplexNumber& other);
	ComplexNumber operator+(const ComplexNumber& other) const;

private:
	friend std::ostream& operator<<(std::ostream& stream, const ComplexNumber& point);
};

