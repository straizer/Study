#pragma once

#include <stdlib.h>
#include <stdio.h>

typedef struct {
    char* value;
    size_t size;
} MyString;

MyString createMyString(size_t);
MyString createFilledMyString(const char*);
MyString copyMyString(MyString);
void writeMyString(FILE*, MyString, const char*);
MyString readMyString(FILE*, size_t);
void deleteMyString(MyString);