#include "InputHandler.h"
#include "OutputHandler.h"

void printInt(int);
void printLong(long);
void printUlong(unsigned long);
template <typename T> void printRealNumber(T);

int main(void)
{
	int _int = getConsoleInput<int>();
	long _long = getConsoleInput<long>();
	unsigned long u_long = getConsoleInput<unsigned long>();
	float _float = getConsoleInput<float>();
	double _double1 = getConsoleInput<double>();
	double _double2 = getConsoleInput<double>();

	cout << "\nWhole numbers:";
	printInt(_int);
	printLong(_long);
	printUlong(u_long);

	cout << "\n\nReal numbers:";
	printRealNumber(_float);
	printRealNumber(_double1);
	printRealNumber(_double2);
}

void printInt(int value)
{
	cout << "\n|";
	printNumber(value);
	cout << "|";
	printNumber(value, 12, true);
	cout << "|";
	printNumber(value, 12, false);
	cout << "|";
}

void printLong(long value)
{
	cout << "\n|";
	printNumber(value);
	cout << "|";
	printNumber(value, 14, true);
	cout << "|";
	printNumber(value, 14, 0, true, false, false);
	cout << "|";
}

void printUlong(unsigned long value)
{
	cout << "\n|";
	printNumber(value);
	cout << "|";
	printNumber(value, 14, true);
	cout << "|";
	printNumber(value, 14, false);
	cout << "|";
}

template <typename T>
void printRealNumber(T value)
{
	cout << "\n|";
	printNumber(value);
	cout << "|";
	printNumber(value, 14, 3, true);
	cout << "|";
	printNumber(value, 0, 0, false, true, true);
	cout << "|";
	printNumber(value, 14, 4, true, true, false);
	cout << "|";
}
