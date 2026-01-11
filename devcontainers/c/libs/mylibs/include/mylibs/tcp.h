#pragma once

#include <netinet/in.h>

#include "./socket.h"
#include "./utils.h"

typedef struct in_addr in_addr;

OUTPUT_DEFINE(startTCPServer, Socket)
startTCPServerOutput startTCPServer(in_port_t server_port, int backlog_size);

OUTPUT_DEFINE(connectToServerViaTCP, Socket)
connectToServerViaTCPOutput connectToServerViaTCP(in_addr server_address, in_port_t server_port);

OUTPUT_DEFINE(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(Socket socket, int how);
