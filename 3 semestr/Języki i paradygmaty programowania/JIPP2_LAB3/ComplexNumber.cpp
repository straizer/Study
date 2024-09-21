#include "ComplexNumber.h"

std::ostream& operator<<(std::ostream& stream, const ComplexNumber& complexNumber)
{
    return stream << "(" << complexNumber.x << ", " << complexNumber.y << ")";
}

ComplexNumber::ComplexNumber(void) : Point() { }

ComplexNumber::ComplexNumber(float x, float y) : Point(x, y) { }

ComplexNumber::ComplexNumber(const Point& point) : Point(point) { }

bool ComplexNumber::operator==(const ComplexNumber& other) const
{
    return Point::operator==(other);
}

bool ComplexNumber::operator!=(const ComplexNumber& other) const
{
    return Point::operator!=(other);
}

ComplexNumber& ComplexNumber::operator+=(const ComplexNumber& other)
{
    *this = dynamic_cast<ComplexNumber&>(Point::operator+=(other));
    return *this;
}

ComplexNumber ComplexNumber::operator+(const ComplexNumber& other) const
{
    return dynamic_cast<ComplexNumber&>(Point::operator+(other));
}
