#include "IOHandler.h"
#include "SeriesCalculator.h"

int main(void)
{
	auto [ epsilon, xValue ] = GetInput();
	double exactValue = exp(xValue);

	double calculatedValue, error;
	size_t componentCount = 0;
	do 
	{
		calculatedValue = GetSum(componentCount, xValue);
		error = CalculateError(calculatedValue, exactValue);
		componentCount++;
	}
	while (error > epsilon);	

	PrintOutput(calculatedValue, componentCount, error);
	WaitForExit();
}