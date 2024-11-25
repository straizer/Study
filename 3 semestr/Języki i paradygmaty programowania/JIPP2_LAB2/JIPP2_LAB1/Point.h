#pragma once

#include <iostream>

using std::string;

class Point
{
public:
	virtual ~Point(void) = default;

	Point(void);
	Point(double x, double y, string name);

	double getX(void) const;
	double getY(void) const;
	const string& getName(void) const;

	void setX(double);
	void setY(double);
	void setName(const string&);

private:
	double x, y;
	string name;

	friend std::ostream& operator<<(std::ostream& stream, const Point& point);
};

