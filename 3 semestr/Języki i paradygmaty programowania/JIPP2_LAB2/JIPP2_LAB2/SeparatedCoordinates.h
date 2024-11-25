#pragma once

#include "Coordinates.h"

#include <tuple>

using std::tuple;

class SeparatedCoordinates : public Coordinates
{
public:
	SeparatedCoordinates(unsigned latitudeHours, unsigned latitudeMinutes, unsigned latitudeSeconds, unsigned longitudeHours, unsigned longitudeMinutes, unsigned longitudeSeconds)
		: Coordinates(latitudeHours + latitudeMinutes / 60 + latitudeSeconds / 3600, longitudeHours + longitudeMinutes / 60 + longitudeSeconds / 3600) { }

	tuple<tuple<unsigned, unsigned, unsigned>, tuple<unsigned, unsigned, unsigned>> getLatitude(void) const;
	double getLongitude(void) const;

	void setLatitude(double newLatitude);
	void setLongitude(double newLongitude);
};

