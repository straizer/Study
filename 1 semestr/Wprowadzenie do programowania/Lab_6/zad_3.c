#include "reader.h"
#include "writer.h"
#include "file.h"

int main(void)
{
    const char* from_file_name = "example.txt";
    const char* to_file_name = "data_3.txt";
    
    FILE* file = openFile(from_file_name, READ);
    char* data = readFile(file);
    closeFile(file);
    
    writeLine(cout, "Data read from file <%s> successfully", from_file_name);
    
    file = openFile(to_file_name, WRITE);
    writeString(file, data, "");
    closeFile(file);
    
    writeLine(cout, "Data written to file <%s> successfully", to_file_name);
    
    free(data);
}