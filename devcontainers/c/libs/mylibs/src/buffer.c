#include "./buffer.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

bool bufferStringIsValid(const char* const string) { return string != nullptr && string[0] != '\0'; }
