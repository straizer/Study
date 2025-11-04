#pragma once

#include <netinet/in.h>

int32_t getTCPSocket(void);

struct in_addr getInternetAddress(const char* ip);
struct sockaddr_in getInternetSocketAddress(struct in_addr internet_address, in_port_t port);

int32_t startTCPServer(in_port_t server_port, int32_t backlog_size);
int32_t connectToServerViaTCP(struct in_addr server_address, in_port_t server_port);

void socketAddressToString(struct sockaddr_in socket_addres, char* out);