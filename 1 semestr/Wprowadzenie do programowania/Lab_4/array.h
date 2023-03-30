#include <stdio.h>

typedef struct {
    int* items;
    size_t size;
} Array;

Array* createArray(size_t);
Array* copyArray(Array*);
void printArray(Array*, char);
void deleteArray(Array*);