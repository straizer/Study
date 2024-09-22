#pragma once

#include "common.h"

typedef enum {
    SUCCESS,
    OVERFLOW,
    UNDERFLOW,
    INCONVERTIBLE
} convert_status;

static const char * const status_names[] = {
	[SUCCESS] = "success",
	[OVERFLOW] = "overflow",
	[UNDERFLOW] = "underflow",
	[INCONVERTIBLE] = "inconvertible"
};

typedef struct {
    convert_status status;
    ullong number;
} ConvertToULLongResult;

typedef struct {
    convert_status status;
    ushort number;
} ConvertToUShortResult;

ConvertToULLongResult convertStrToULLong(const char*);
ConvertToUShortResult convertStrToUShort(const char*);