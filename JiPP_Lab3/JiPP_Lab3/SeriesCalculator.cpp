#include "SeriesCalculator.h"

#include <math.h>

double GetSum(int componentCount, double xValue)
{
	double result = 0;
	double currentElement = 1;
	for (int i = 0; i < componentCount; i++)
	{
		result += currentElement;
		currentElement *= xValue / (i + 1);
	}
	return result;
}

double CalculateError(double calculatedValue, double exactValue)
{
	return fabs(calculatedValue - exactValue) / fabs(exactValue);
}
