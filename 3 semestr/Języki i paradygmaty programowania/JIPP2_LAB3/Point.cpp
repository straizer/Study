#include "Point.h"

Point::Point(void) : Point(0, 0) { }

Point::Point(float x, float y) : x(x), y(y) { }

bool Point::operator==(const Point& other) const
{
	return x == other.x && y == other.y;
}

bool Point::operator!=(const Point& other) const
{
	return !(*this == other);
}

Point& Point::operator+=(const Point& other)
{
	x += other.x;
	y += other.y;
	return *this;
}

Point Point::operator+(const Point& other) const
{
	Point newPoint(*this);
	return newPoint += other;
}

Point getMiddlePoint(const Point& p1, const Point& p2)
{
	return Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2);
}

float getDistance(const Point& p1, const Point& p2)
{
	float xDiff = p1.x - p2.x;
	float yDiff = p1.y - p2.y;
	return std::sqrt(xDiff * xDiff + yDiff * yDiff);
}

std::ostream& operator<<(std::ostream& stream, const Point& point)
{
	return stream << "Point: (" << point.x << ", " << point.y << ")";
}

std::istream& operator>>(std::istream& stream, Point& point)
{
	return stream >> point.x >> point.y;
}
