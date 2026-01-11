#pragma once

#include <stddef.h>

#include "../include/mylibs/macros.h"

OUTPUT_DEFINE(closeFileDescriptor, nullptr_t)
closeFileDescriptorOutput closeFileDescriptor(int file_descriptor);