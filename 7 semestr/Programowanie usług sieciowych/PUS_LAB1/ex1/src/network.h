#pragma once

#include <netinet/in.h>

in_port_t getPort(const char* port_string);

void connectToSocket(int32_t via_socket, struct sockaddr_in to_address);
void closeConnection(int32_t via_socket);