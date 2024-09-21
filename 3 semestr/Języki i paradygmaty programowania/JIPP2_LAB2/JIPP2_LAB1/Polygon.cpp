#include "Polygon.h"

#include <cmath>

using std::ostream;
using std::endl;

Polygon::Polygon(size_t maxPointsCount, string name) : maxPointsCount(maxPointsCount), name(name)
{
	points = new Point[maxPointsCount];
}

Polygon::Polygon(const Polygon& other) : Polygon(other.maxPointsCount, other.name)
{
	for (int i = 0; i < maxPointsCount; i++)
		points[i] = other.points[i];
}

Polygon::~Polygon(void)
{
	delete[] points;
}

double getDistanceBetweenPoints(const Point& p1, const Point& p2)
{
	double xDiff = p1.getX() - p2.getX();
	double yDiff = p1.getY() - p2.getY();
	return sqrt(xDiff * xDiff + yDiff * yDiff);
}

double Polygon::getCircumference(void) const
{
	double circumference = 0;
	for (size_t i = 0; i < maxPointsCount - 1; i++)
		circumference += getDistanceBetweenPoints(points[i], points[i + 1]);
	circumference += getDistanceBetweenPoints(points[0], points[maxPointsCount - 1]);
	return circumference;
}

void Polygon::setName(const string& newName)
{
	name = newName;
}

Point& Polygon::operator[](size_t idx)
{
	return points[idx];
}

const Point& Polygon::operator[](size_t idx) const
{
	return points[idx];
}

Polygon& Polygon::operator=(const Polygon& other)
{
	if (this == &other)
		return *this;

	maxPointsCount = other.maxPointsCount;
	name = other.name;

	delete[] points;
	points = new Point[maxPointsCount];
	for (int i = 0; i < maxPointsCount; i++)
		points[i] = other.points[i];

	return *this;
}

ostream& operator<<(ostream& stream, const Polygon& polygon)
{
	stream << "Polygon " << polygon.name << endl;
	for (size_t i = 0; i < polygon.maxPointsCount; i++)
		stream << "  " << polygon[i] << endl;
	return stream;
}
