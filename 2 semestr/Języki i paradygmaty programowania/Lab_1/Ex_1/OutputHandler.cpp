#include "OutputHandler.h"

#include <iomanip>

using std::setprecision;
using std::setw;
using std::showpos;
using std::noshowpos;
using std::scientific;
using std::fixed;
using std::defaultfloat;
using std::left;
using std::right;

void _prepareOutput(size_t width, size_t precision, bool showPositiveSign, bool scientificNotation, bool alignLeft)
{
	if (width)
		cout << setw(width);
	cout << setprecision(precision ? precision : 6) << (showPositiveSign ? showpos : noshowpos) << (scientificNotation ? scientific : fixed) << (alignLeft ? left : right);
}

void _resetOutput(void)
{
	cout << setprecision(6) << noshowpos << defaultfloat << left;
}
