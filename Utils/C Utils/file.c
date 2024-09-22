#include "file.h"

#include "error.h"
#include <string.h>

FILE* openFile(const char* path, OpenMode mode)
{
    FILE* file = fopen(path, mode == READ ? "r" : mode == WRITE ? "w" : "");
    if (!file)
        error("ERROR WHILE OPENING FILE <%s>", path);
    return file;
}

void closeFile(FILE* file)
{
    switch (fclose(file))
    {
        case 0:
            return;
        case EOF:
            error("ERROR WHILE CLOSING FILE");
        default:
            error("ERROR - UNDEFINED BEHAVIOR");
    }
}

char* readFile(FILE* file)
{
    long file_size = getFileSize(file);
    char* data = malloc((file_size + 1) * sizeof(char));
    if (!data)
        error("ERROR WHILE READING FILE");
    size_t read_length = 0;
    while (1)
    {
        char* read_line = malloc((file_size - read_length + 1) * sizeof(char));
        if (!read_line || !fgets(read_line, file_size, file))
            error("ERROR WHILE READING FILE");
        strcpy(data + read_length * sizeof(char), read_line);
        read_length += strlen(read_line);
        free(read_line);
        if (read_length == file_size)
            return data;
    }
}

long getFileSize(FILE* file)
{
    long current_position = ftell(file);
    
    if(fseek(file, 0, SEEK_END) != 0)
        error("ERROR WHILE OBTAINING FILE SIZE");
    long file_size = ftell(file);
    
    if(fseek(file, current_position, SEEK_SET) != 0)
        error("ERROR WHILE OBTAINING FILE SIZE");
        
    return file_size;
}