#pragma once

#include <iostream>

using std::cin;
using std::cout;
using std::string;
using std::istream;

template <typename T> T getInput(const string& = "", istream& = cin);
template<> string getInput(const string&, istream&);
bool _inputSuccessful(istream&);

template <typename T> T getInput(const string& prompt, istream& stream)
{
	if (prompt.empty())
		cout << "Enter " << typeid(T).name() << ": ";
	else
		cout << prompt;
	T input{};
	while (true)
	{
		stream >> input;
		if (_inputSuccessful(stream))
			break;
		cout << "Wrong input. Try again: ";
	}
	return input;
}
