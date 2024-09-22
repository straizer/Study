#pragma once

#include <stdio.h>
#include "common.h"

#define cout stdout

void write(FILE*, const char*, ...);
void writeLine(FILE*, const char*, ...);
void writeNewLine(FILE*);

void writeInt(FILE*, int, const char*);
void writeString(FILE*, const char*, const char*);
void writeULLong(FILE*, ullong, const char*);
void writeChar(FILE*, char, const char*);
void writeUShort(FILE*, ushort, const char*);
