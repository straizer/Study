#include "reader.h"
#include "writer.h"
#include "file.h"

int main(void)
{
    const char* file_name = "data_2.txt";
    
    write(cout, "Enter string: ");
    FILE* file = openFile(file_name, WRITE);
    writeString(file, readString(cin), "");
    closeFile(file);
    
    write(cout, "String written to file <%s> successfully", file_name);
}