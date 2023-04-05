#pragma once

#include <iostream>

using std::cin;
using std::string;
using std::istream;


/// <summary>
/// Reads {T} from given stream.
/// </summary>
/// <typeparam name="T">- type of input to read</typeparam>
/// <param name="prompt">- optional prompt to output stream; default is "Enter {T}: "</param>
/// <param name="stream">- optional input stream; default is cin</param>
/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
/// <returns>Read {T} from given stream.</returns>
template <typename T> T getInput(const string& prompt = "", istream& stream = cin);

/// <summary>
/// Reads string from given stream.
/// </summary>
/// <param name="prompt">- optional prompt to output stream; default is "Enter string: "</param>
/// <param name="stream">- optional input stream; default is cin</param>
/// <exception cref="std::bad_alloc">If allocation fails.</exception>
/// <exception cref="std::length_error">If total size of string is too big.</exception>
/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
/// <returns>Read string from given stream.</returns>
template<> string getInput(const string& prompt, istream& stream);

/// <summary>
/// Checks if state of given stream is good. Clears state if it's not.
/// </summary>
/// <param name="stream">- input stream</param>
/// <exception cref="std::ios_base::failure">If any unhandled exeption was thrown by stream. Leaves the stream in a valid state.</exception>
/// <returns>true if state of given stream was good; false otherwise.</returns>
bool _isStreamGood(istream& stream);


template <typename T> T getInput(const string& prompt, istream& stream)
{
	if (prompt.empty())
		*stream.tie() << "Enter " << typeid(T).name() << ": ";
	else
		*stream.tie() << prompt;
	T input{};
	while (true)
	{
		stream >> input;
		if (_isStreamGood(stream))
			break;
		*stream.tie() << "Wrong input. Try again: ";
	}
	return input;
}
