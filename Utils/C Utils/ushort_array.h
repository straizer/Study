#pragma once

#include "common.h"
#include <stdlib.h>
#include <stdio.h>

typedef struct {
    ushort* items;
    size_t size;
} UShortArray;

UShortArray createUShortArray(size_t);
UShortArray copyUShortArray(UShortArray);
void writeUShortArray(FILE*, UShortArray, const char*);
UShortArray readUShortArray(FILE*, const char*);
void deleteUShortArray(UShortArray);