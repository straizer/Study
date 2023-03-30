#include "reader.h"
#include "writer.h"
#include "file.h"

int main(void)
{
    const char* file_name = "data_1.txt";
    FILE* file;
    
    write(cout, "Enter 5 numbers separated by space: ");
    file = openFile(file_name, WRITE);
    for (size_t i = 0; i < 5; i++)
        writeInt(file, readInt(cin), " ");
    closeFile(file);  
    
    writeLine(cout, "Numbers saved to file <%s>", file_name);
    
    write(cout, "Numbers read from file <%s>: ", file_name);
    file = openFile(file_name, READ);
    for (size_t i = 0; i < 5; i++)
        writeInt(cout, readInt(file), " ");
    closeFile(file);  
}
