#pragma once

#include <stdint.h>

#include "./macros.h"

OUTPUT_DEFINE(getPort, uint16_t)
getPortOutput getPort(const char* port_string);
