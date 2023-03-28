#include "IOHandler.h"

#include <iostream>

using std::cin;
using std::cout;
using std::endl;
using std::scientific;

pair<double, double> GetInput(void)
{
	cout << "Program calculates how many components of series 'x^n/n!' are necessary for error to be below certain epsilon value." << endl;
	cout << "Input epsilon and x value: ";
	double epsilon, xValue;
	if (cin >> epsilon >> xValue)
		return { epsilon, xValue };
	cout << endl << "Error!";
	exit(1);
}

void PrintOutput(double calculatedValue, size_t componentCount, double error)
{
	cout.precision(14);
	cout << scientific;
	cout << "Calculated value = " << calculatedValue << endl;
	cout << "Component count = " << componentCount << endl;
	cout << "Error = " << error << endl;
}

void WaitForExit(void)
{
	system("pause");
}
