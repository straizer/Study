#pragma once

#include <stddef.h>

#include <mylibs/utils.h>
#include <netinet/in.h>

typedef struct sockaddr_in sockaddr_in;

OUTPUT_DEFINE(getPort, in_port_t)
getPortOutput getPort(const char* port_string);

OUTPUT_DEFINE(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(int32_t via_socket, sockaddr_in to_address);

OUTPUT_DEFINE(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(int32_t via_socket, uint8_t how);