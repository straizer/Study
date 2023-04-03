#pragma once

#include <iostream>

using std::cout;

template <typename T> void printNumber(T);
template <typename T> void printNumber(T, size_t, bool);
template <typename T> void printNumber(T, size_t, size_t, bool);
template <typename T> void printNumber(T, size_t, size_t, bool, bool, bool);
void _prepareOutput(size_t, size_t, bool, bool, bool);
void _resetOutput(void);

template <typename T>
void printNumber(T number)
{
	printNumber(number, 0, true);
}

template <typename T>
void printNumber(T number, size_t width, bool alignLeft)
{
	printNumber(number, width, 0, alignLeft);
}

template <typename T>
void printNumber(T number, size_t width, size_t precision, bool alignLeft)
{
	printNumber(number, width, precision, false, false, alignLeft);
}

template <typename T>
void printNumber(T number, size_t width, size_t precision, bool showPositiveSign, bool scientificNotation, bool alignLeft)
{
	_prepareOutput(width, precision, showPositiveSign, scientificNotation, alignLeft);
	cout << number;
	_resetOutput();
}