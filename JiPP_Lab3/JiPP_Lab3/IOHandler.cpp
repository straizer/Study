#include "IOHandler.h"

#include <iostream>

using std::cin;
using std::cout;
using std::endl;
using std::scientific;

pair<int, double> GetInput(void)
{
	cout << "Program calculates sum of 'n' first elements of series 'x^n/n!'" << endl;
	cout << "Input component count and x value: ";
	int componentCount;
	double x_value;
	if (cin >> componentCount >> x_value)
		return { componentCount, x_value };
	cout << endl << "Error!";
	exit(1);
}

void PrintOutput(double exactValue, double calculatedValue, double error)
{
	cout.precision(14);
	cout << scientific;
	cout << "Exact value = " << exactValue << endl;
	cout << "Calculated value = " << calculatedValue << endl;
	cout << "Error = " << error << endl;
}

void WaitForExit(void)
{
	system("pause");
}
