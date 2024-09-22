#pragma once

#include "common.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct {
    double* items;
    size_t row;
    size_t column;
} DoubleMatrix;

DoubleMatrix createDoubleMatrix(size_t, size_t);
void deleteDoubleMatrix(DoubleMatrix);
