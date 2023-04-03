#include "InputHandler.h"

#include <string>

using std::getline;
using std::ws;
using std::streamsize;
using std::numeric_limits;

template<> string getConsoleInput(const string& prompt)
{
	cout << (prompt.empty() ? "Enter string: " : prompt);
	string input;
	while (!getline(cin >> ws, input))
		cout << "Wrong input. Try again: ";
	return input;
}

bool _inputSuccessful(void)
{
	bool success = cin.good();
	cin.clear();
	cin.ignore(numeric_limits<streamsize>::max(), '\n');
	return success;
}
