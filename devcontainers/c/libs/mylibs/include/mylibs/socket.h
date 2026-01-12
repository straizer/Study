#pragma once

#include <sys/socket.h>

#include "./macros.h"

typedef struct sockaddr sockaddr;

typedef struct {
    int file_descriptor;
} Socket;

typedef STRUCT_ALIGNED(16) {
    sockaddr* const value;
    socklen_t length;
}
SocketAddress;

DECLARATION(socketCreate, const Socket, int domain, int type, int protocol)
DECLARATION_VOID(socketConnect, const Socket* socket, const SocketAddress* address)
DECLARATION_VOID(socketBind, const Socket* socket, const SocketAddress* address)
DECLARATION_VOID(socketListen, const Socket* socket, int backlog_size)
DECLARATION(socketAccept, const Socket, const Socket* socket, SocketAddress* address)
DECLARATION_VOID(socketShutdown, const Socket* socket, int how)
DECLARATION_VOID(socketClose, const Socket* socket)