#pragma once

#include "Point.h"

class Polygon
{
public:
	Polygon(size_t maxPointsCount, string name);
	Polygon(const Polygon& other);

	virtual ~Polygon(void);

	double getCircumference(void) const;

	void setName(const string& newName);

	Point& operator[](size_t idx);
	const Point& operator[](size_t idx) const;

	Polygon& operator=(const Polygon& other);

	friend std::ostream& operator<<(std::ostream& stream, const Polygon& polygon);

private:
	Point* points;
	size_t maxPointsCount;
	string name;
};

