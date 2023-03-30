#include "SeriesCalculator.h"

#include <math.h>

double getExactValue(const double& xValue)
{
	return exp(xValue);
}

double GetSum(const size_t& componentCount, const double& xValue)
{
	double result = 0;
	double currentElement = 1;
	for (int i = 0; i < componentCount; )
	{
		result += currentElement;
		i++;
		currentElement *= xValue / i;
	}
	return result;
}

double CalculateError(const double& calculatedValue, const double& exactValue)
{
	return fabs(calculatedValue - exactValue) / fabs(exactValue);
}
