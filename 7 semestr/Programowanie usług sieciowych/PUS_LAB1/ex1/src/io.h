#pragma once

__attribute__((format(printf, 1, 2))) void printOutput(const char* format, ...);
__attribute__((format(printf, 1, 2))) void printError(const char* format, ...);