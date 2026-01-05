#pragma once

#include <netinet/in.h>

typedef struct sockaddr_in sockaddr_in;
typedef struct sockaddr sockaddr;

in_port_t getPort(const char* port_string);

void connectToSocket(int32_t via_socket, sockaddr_in to_address);
void closeConnection(int32_t via_socket);