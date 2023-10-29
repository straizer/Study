#include "SeparatedCoordinates.h"

using std::make_tuple;

tuple<tuple<unsigned, unsigned, unsigned>, tuple<unsigned, unsigned, unsigned>> SeparatedCoordinates::getLatitude(void) const
{
	unsigned latitudeHours = latitude;
	unsigned latitudeMinutes = (latitude - latitudeHours) * 60;
	unsigned latitudeSeconds = (latitudeMinutes - latitudeHours) / 60;
	return make_tuple()
}
