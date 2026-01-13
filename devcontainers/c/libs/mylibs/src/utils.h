#pragma once

#include "../include/mylibs/macros.h"

DECLARATION_VOID(utilsClose, int* file_descriptor)
DECLARATION(utilsMalloc, void* const, size_t size)
void utilsFree(void* ptr);