#pragma once

#include <stdint.h>

#include <sys/socket.h>

#include "utils.h"

typedef struct sockaddr sockaddr;

OUTPUT_DEFINE(getPort, uint16_t)
getPortOutput getPort(const char* port_string);

OUTPUT_DEFINE(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(int32_t via_socket, const sockaddr* to_address, socklen_t to_address_len);