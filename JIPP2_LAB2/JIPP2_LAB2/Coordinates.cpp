#include "Coordinates.h"

Coordinates::Coordinates(double latitude, double longitude) : latitude(latitude), longitude(longitude) { }

double Coordinates::getLatitude(void) const
{
	return latitude;
}

double Coordinates::getLongitude(void) const
{
	return longitude;
}

void Coordinates::setLatitude(double newLatitude)
{
	latitude = newLatitude;
}

void Coordinates::setLongitude(double newLongitude)
{
	longitude = newLongitude;
}
