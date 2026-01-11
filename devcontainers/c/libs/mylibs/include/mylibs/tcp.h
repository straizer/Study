#pragma once

#include <netinet/in.h>

#include "./macros.h"
#include "./socket.h"

typedef struct in_addr in_addr;

DECLARATION(startTCPServer, Socket, in_port_t server_port, int backlog_size)
DECLARATION(connectToServerViaTCP, Socket, in_addr server_address, in_port_t server_port)
DECLARATION_VOID(closeConnection, Socket socket, int how)
