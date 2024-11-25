#include <iostream>
#include <fstream>
#include "../Common/StreamHelper.h"

const size_t MAX_NAME_SIZE = 23;
const std::string DEFAULT_FILENAME = "card.txt";

std::string getName(void);
std::string formatName(std::string);
std::string prepareOutput(const std::string&);
void writeToFile(const std::string&, const std::string& = DEFAULT_FILENAME);

int main(void)
{
    std::string name = getName();
    std::string formattedName = formatName(name);
    std::string output = prepareOutput(formattedName);
    writeToFile(output);
}

std::string getName(void)
{
    std::cout << "Enter name and surname: ";
    return readLine(std::cin);
}

std::string formatName(std::string name)
{
    if (name.size() >= MAX_NAME_SIZE)
        return name.substr(0, MAX_NAME_SIZE);
    size_t sizeDiff = MAX_NAME_SIZE - name.size();
    size_t leftPad = sizeDiff / 2;
    size_t rightPad = sizeDiff - leftPad;
    return name.insert(name.size(), rightPad, ' ').insert(0, leftPad, ' ');
}

std::string prepareOutput(const std::string& formattedName)
{
    std::string cardTemplate =
        "*****************************\n"
        "*  _______________________  *\n"
        "* /                       \\ *\n"
        "* |$| *\n"
        "* \\_______________________/ *\n"
        "*                           *\n"
        "*****************************";
    return cardTemplate.replace(cardTemplate.find('$'), 1, formattedName);
}

void writeToFile(const std::string& data, const std::string& fileName)
{
    std::ofstream stream(fileName, std::ios_base::out);
    if (stream.is_open())
        stream << data;
    std::cout << "Card saved to file '" << fileName << "'" << std::endl;
}