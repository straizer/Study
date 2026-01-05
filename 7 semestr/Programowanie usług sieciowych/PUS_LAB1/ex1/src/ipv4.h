#pragma once

#include <netinet/in.h>

typedef struct in_addr in_addr;
typedef struct sockaddr_in sockaddr_in;

in_addr getInternetAddress(const char* ip);

int32_t startTCPServer(in_port_t server_port, int32_t backlog_size);
int32_t connectToServerViaTCP(in_addr server_address, in_port_t server_port);

void socketAddressToString(sockaddr_in socket_address, char* out);