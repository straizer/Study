#include "InputHandler.h"

#include <string>

using std::getline;
using std::ws;
using std::streamsize;
using std::numeric_limits;

template<> string getInput(const string& prompt, istream& stream)
{
	cout << (prompt.empty() ? "Enter string: " : prompt);
	string input;
	while (!getline(stream >> ws, input))
		cout << "Wrong input. Try again: ";
	return input;
}

bool _inputSuccessful(istream& stream)
{
	bool success = stream.good();
	stream.clear();
	stream.ignore(numeric_limits<streamsize>::max(), '\n');
	return success;
}