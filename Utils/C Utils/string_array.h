#pragma once

#include "my_string.h"
#include <stdlib.h>
#include <stdio.h>

typedef struct {
    MyString* items;
    size_t size;
} MyStringArray;

MyStringArray createMyStringArray(size_t);
MyStringArray copyMyStringArray(MyStringArray);
void writeMyStringArray(FILE*, MyStringArray, const char*);
void deleteMyStringArray(MyStringArray);