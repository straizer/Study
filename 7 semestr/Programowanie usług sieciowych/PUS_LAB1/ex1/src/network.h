#pragma once

#include <netinet/in.h>
#include <stddef.h>

#include "utils.h"

typedef struct sockaddr_in sockaddr_in;

OUTPUT_DEFINE(getPort, in_port_t)
getPortOutput getPort(const char* port_string);

void connectToSocket(int32_t via_socket, sockaddr_in to_address);
void closeConnection(int32_t via_socket);