#include <stdio.h>

#define cout stdout

void write(FILE*, const char*, ...);
void writeLine(FILE*, const char*, ...);

void writeInt(FILE*, int, const char*);
void writeString(FILE*, const char*, const char*);
