#include "reader.h"
#include "writer.h"
#include "file.h"
#include <ctype.h>

int main(void)
{
    const char* file_name = "example.txt";
    
    size_t letters_count[26] = {0};
    
    FILE* file = openFile(file_name, READ);
    long file_size = getFileSize(file);
    for (size_t i = 0; i < file_size; i++)
    {
        char character = readChar(file);
        if ((character > 64 && character < 91) || (character > 96 && character < 123))
            letters_count[toupper(character) - 'A']++;
    }
    closeFile(file);
    
    writeLine(cout, "Number of each letter in file <%s>:", file_name);
    for (size_t i = 0; i < 26; i++)
        writeLine(cout, "%c: %4d", (char)(i + 'A'), letters_count[i]);
}