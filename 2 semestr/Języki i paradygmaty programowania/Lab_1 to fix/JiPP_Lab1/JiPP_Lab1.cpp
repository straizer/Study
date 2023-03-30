#include <iostream>
#include <iomanip>

using std::cout;
using std::cin;
using std::endl;
using std::setw;
using std::scientific;
using std::fixed;
using std::showpos;
using std::noshowpos;
using std::left;
using std::right;

template <typename T> void printWithBorders(T);
template <typename T> void printWithBorders(T, size_t, bool);
template <typename T> void printWithBorders(T, size_t, size_t, bool);
template <typename T> void printWithBorders(T, size_t, size_t, bool, bool, bool);

int main(void)
{
	int _int;
	long _long;
	unsigned long u_long;
	float _float;
	double _double1, _double2;

	cout << "Enter 3 whole numbers (separated by space): ";
	cin >> _int >> _long >> u_long;

	cout << "Enter 3 real numbers (separated by space): ";
	cin >> _float >> _double1 >> _double2;

	cout << endl << "Liczby Calkowite:" << endl << "|";

	printWithBorders(_int);
	printWithBorders(_int, 12, true);
	printWithBorders(_int, 12, false);
	cout << endl << "|";

	printWithBorders(_long);
	printWithBorders(_long, 14, true);
	printWithBorders(_long, 14, false);
	cout << endl << "|";

	printWithBorders(u_long);
	printWithBorders(u_long, 14, true);
	printWithBorders(u_long, 14, false);
	cout << endl << endl;

	cout << "Liczby Rzeczywiste:" << endl << "|";

	printWithBorders(_float);
	printWithBorders(_float, 14, 3, true);
	printWithBorders(_float, 0, 0, false, true, true);
	printWithBorders(_float, 14, 4, true, true, false);
	cout << endl << "|";

	printWithBorders(_double1);
	printWithBorders(_double1, 14, 3, true);
	printWithBorders(_double1, 0, 0, false, true, true);
	printWithBorders(_double1, 14, 4, true, true, false);
	cout << endl << "|";

	printWithBorders(_double2);
	printWithBorders(_double2, 14, 3, true);
	printWithBorders(_double2, 0, 0, false, true, true);
	printWithBorders(_double2, 14, 4, true, true, false);

	//cout << setw(12) << _int << setw(12) << _long << setw(12) << u_long << endl;
	//cout << endl << "Liczby Rzeczywiste" << scientific << showpos << endl;
	//cout.precision(4);
	//cout << setw(14) << _float << setw(14) << _double1 << setw(14) << _double2 << endl;
	//getchar();

	//cout << endl << endl << endl;
	//cout << endl << "Liczby Calkowite:" << endl << "-----------------------" << endl << endl;
	//cout << 

}

template <typename T>
void printWithBorders(T number)
{
	cout << number << "|";
}

template <typename T>
void printWithBorders(T number, size_t width, bool alignLeft)
{
	if (width)
		cout << setw(width);
	cout << (alignLeft ? left : right);
	printWithBorders(number);
}

template <typename T>
void printWithBorders(T number, size_t width, size_t precision, bool alignLeft)
{
	if (precision)
		cout.precision(precision);
	printWithBorders(number, width, alignLeft);
}

template <typename T>
void printWithBorders(T number, size_t width, size_t precision, bool showPos, bool scientific_notation, bool alignLeft)
{
	cout << (showPos ? showpos : noshowpos) << (scientific_notation ? scientific : fixed);
	printWithBorders(number, width, precision, alignLeft);
}
