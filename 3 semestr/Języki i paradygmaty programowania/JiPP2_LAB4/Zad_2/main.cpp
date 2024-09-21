#include <iostream>
#include <vector>
#include <string>
#include <fstream>

const std::string DEFAULT_CSV_FILENAME = "dataQuotes.csv";

std::vector<std::string> loadCsv(const std::string& = DEFAULT_CSV_FILENAME);

int main(void)
{
    std::cout << "Hello World!\n";
}

std::vector<std::string> loadCsv(const std::string& fileName)
{
    
}