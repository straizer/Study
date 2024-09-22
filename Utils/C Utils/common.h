#pragma once

#include <stdbool.h>
#include <stdlib.h>
#include "string_array.h"

typedef unsigned long long int ullong;
typedef unsigned short int ushort;

MyStringArray splitString(const char*, const char*);
size_t countSubstrings(const char*, const char*);
char* copyString(const char*);