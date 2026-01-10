#include "../include/mylibs/string.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

bool stringIsValid(const char* const string) { return string != nullptr && string[0] != '\0'; }
