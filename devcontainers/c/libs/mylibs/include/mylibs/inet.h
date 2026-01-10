#pragma once

#include <stdint.h>

#include "./utils.h"

OUTPUT_DEFINE(getPort, uint16_t)
getPortOutput getPort(const char* port_string);
