#include "error.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>

void error(const char* error, ...)
{
    char* formatted_error = malloc((strlen(error) + 16) * sizeof(char));
    strcat(formatted_error, "\033[1m\033[31m\n");
    strcat(formatted_error, error);
    strcat(formatted_error, "\n\033[0m");
    
    va_list args;
    va_start(args, error);
    vfprintf(stderr, formatted_error, args);
    va_end(args);
    
    free(formatted_error);
    exit(EXIT_FAILURE);
}