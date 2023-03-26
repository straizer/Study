#include "IOHandler.h"
#include "SeriesCalculator.h"

int main(void)
{
	auto [ componentCount, xValue ] = GetInput();

	double calculatedValue = GetSum(componentCount, xValue);
	double exactValue = exp(xValue);
	double error = CalculateError(calculatedValue, exactValue);

	PrintOutput(exactValue, calculatedValue, error);

	WaitForExit();
}