#pragma once

#include "Point.h"

class Polygon
{
public:
	Polygon(size_t maxPointsCount);

	virtual ~Polygon(void);

	double getCircumference(void) const;

	Point& operator[](size_t idx);
	const Point& operator[](size_t idx) const;

	friend std::ostream& operator<<(std::ostream& stream, const Polygon& polygon);

private:
	Point* points;
	size_t maxPointsCount;
};

