#include <stdio.h>
#include <stdlib.h>

typedef enum 
{
    READ,
    WRITE
} 
OpenMode;

FILE* openFile(const char*, OpenMode);
void closeFile(FILE*);
char* readFile(FILE*);
long getFileSize(FILE*);
