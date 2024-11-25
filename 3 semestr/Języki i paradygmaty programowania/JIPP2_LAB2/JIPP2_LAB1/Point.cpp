#include "Point.h"

using std::ostream;
using std::endl;

Point::Point(void) : Point(0, 0, "") { }

Point::Point(double x, double y, string name) : x(x), y(y), name(name) { }

double Point::getX(void) const
{
	return x;
}

double Point::getY(void) const
{
	return y;
}

const string& Point::getName(void) const
{
	return name;
}

void Point::setX(double newX)
{
	x = newX;
}

void Point::setY(double newY)
{
	y = newY;
}

void Point::setName(const string& newName)
{
	name = newName;
}

ostream& operator<<(ostream& stream, const Point& point)
{
	return stream 
		<< "Point " 
		<< point.name 
		<< " = (" 
		<< point.x 
		<< ", " 
		<< point.y 
		<< ")";
}
