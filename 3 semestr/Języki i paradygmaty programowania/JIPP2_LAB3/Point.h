#pragma once

#include <iostream>

class Point
{
public:
	virtual ~Point(void) = default;

	Point(void);
	Point(float x, float y);

	bool operator==(const Point& other) const;
	bool operator!=(const Point& other) const;
	Point& operator+=(const Point& other);
	Point operator+(const Point& other) const;

protected:
	float x, y;

private:
	friend Point getMiddlePoint(const Point& p1, const Point& p2);
	friend float getDistance(const Point& p1, const Point& p2);
	friend std::ostream& operator<<(std::ostream& stream, const Point& point);
	friend std::istream& operator>>(std::istream& stream, Point& point);
};

