#include <stdio.h>

typedef struct {
    int* items;
    size_t size;
} Array;

Array createArray(size_t);
void printArray(Array);
void deleteArray(Array);