#pragma once

class Coordinates
{
protected:
	double latitude, longitude;

public:
	Coordinates(double latitude, double longitude);
	virtual ~Coordinates(void) = default;

	double getLatitude(void) const;
	double getLongitude(void) const;

	void setLatitude(double newLatitude);
	void setLongitude(double newLongitude);
};

