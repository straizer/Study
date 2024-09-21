#include "StreamHelper.h"

#include <string>

std::string readLine(std::istream& stream)
{
    std::string name;
    std::getline(stream, name);
    return name;
}
