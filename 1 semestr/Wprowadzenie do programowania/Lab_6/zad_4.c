#include "writer.h"
#include "file.h"

int main(void)
{
    const char* file_name = "example.txt";
    
    write(cout, "Number of characters in file <%s>: ", file_name);
    FILE* file = openFile(file_name, READ);
    writeInt(cout, getFileSize(file), "");
    closeFile(file);
}