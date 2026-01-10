#pragma once

#include <netinet/in.h>

#include "utils.h"

typedef struct in_addr in_addr;
typedef struct sockaddr_in sockaddr_in;

OUTPUT_DEFINE(getInternetAddress, in_addr)
getInternetAddressOutput getInternetAddress(const char* ip);

OUTPUT_DEFINE(startTCPServer, int)
startTCPServerOutput startTCPServer(in_port_t server_port, int backlog_size);

OUTPUT_DEFINE(connectToServerViaTCP, int)
connectToServerViaTCPOutput connectToServerViaTCP(in_addr server_address, in_port_t server_port);

OUTPUT_DEFINE(socketAddressToString, nullptr_t)
socketAddressToStringOutput socketAddressToString(sockaddr_in socket_address, char* out, size_t out_size);

OUTPUT_DEFINE(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(int via_socket, int how);