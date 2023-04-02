#include <iostream>

using std::cout;
using std::cin;
using std::endl;
using std::string;
using std::invalid_argument;

template<typename T> void getInput(T&);
template<> void getInput(string&);
template<typename T> void printOutput(T&);

int main(void)
{
    int inputInt;
    double inputDouble;
    string inputString;

    try
    {
        getInput(inputInt);
        getInput(inputDouble);
        getInput(inputString);
    }
    catch (const invalid_argument& ex)
    {
        cout << ex.what();
    }

    printOutput(inputInt);
    printOutput(inputDouble);
    printOutput(inputString);

    system("pause");
}

template<typename T>
void getInput(T& input)
{
    cout << "Enter " << typeid(T).name() << ": ";
    if (!(cin >> input))
        throw invalid_argument("Invalid argument\n");
}

template <>
void getInput(string& input)
{
    cout << "Enter string: ";
    if (!(cin >> input))
        throw invalid_argument("Invalid argument\n");
}

template<typename T>
void printOutput(T& output)
{
    cout << output << endl;
}