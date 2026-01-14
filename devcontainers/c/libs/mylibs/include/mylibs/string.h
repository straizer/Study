#pragma once

#include "./macros.h"

typedef STRUCT_ALIGNED(32) {
    char* data;
    size_t length;
    size_t capacity;
}
String;

DECLARATION_ALIGNED(stringFormatNew, String, 64, size_t capacity, const char* format, ...)

String stringMove(String* string);
void stringDestroy(const String* string);
#define AUTO_STRING __attribute__((cleanup(stringDestroy))) String
