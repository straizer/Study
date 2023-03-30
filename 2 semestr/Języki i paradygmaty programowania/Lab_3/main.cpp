#include "IOHandler.h"
#include "SeriesCalculator.h"

int main(void)
{
	auto [ epsilon, xValue ] = GetInput();
	double exactValue = getExactValue(xValue);

	double calculatedValue, error;
	size_t componentCount = 0;
	do 
	{
		componentCount++;
		calculatedValue = GetSum(componentCount, xValue);
		error = CalculateError(calculatedValue, exactValue);
	}
	while (error > epsilon);	

	PrintOutput(calculatedValue, componentCount, error);
	WaitForExit();
}