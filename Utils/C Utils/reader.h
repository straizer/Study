#pragma once

#include <stdio.h>
#include "common.h"

#define cin stdin

int readInt(FILE*);
char* readString(FILE*);
char* readKnownSizeString(FILE*, size_t);
char readChar(FILE*);

ullong tryReadULLong(FILE*);
ushort tryReadUShort(FILE*);