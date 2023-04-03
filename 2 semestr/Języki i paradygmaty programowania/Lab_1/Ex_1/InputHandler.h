#pragma once

#include <iostream>

using std::cin;
using std::cout;
using std::string;

template <typename T> T getConsoleInput(const string & = "");
template<> string getConsoleInput(const string&);
bool _inputSuccessful(void);

template <typename T> T getConsoleInput(const string& prompt)
{
	if (prompt.empty())
		cout << "Enter " << typeid(T).name() << ": ";
	else
		cout << prompt;
	T input;
	while (true)
	{
		cin >> input;
		if (_inputSuccessful())
			break;
		cout << "Wrong input. Try again: ";
	}
	return input;
}
