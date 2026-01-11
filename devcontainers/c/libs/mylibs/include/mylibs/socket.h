#pragma once

#include <sys/socket.h>

#include "./utils.h"

typedef struct sockaddr sockaddr;

typedef struct {
    int file_descriptor;
} Socket;

OUTPUT_DEFINE(socketCreate, Socket)
socketCreateOutput socketCreate(int domain, int type, int protocol);

OUTPUT_DEFINE(socketConnect, nullptr_t)
socketConnectOutput socketConnect(const Socket* socket, const sockaddr* address, socklen_t address_length);

OUTPUT_DEFINE(socketBind, nullptr_t)
socketBindOutput socketBind(const Socket* socket, const sockaddr* address, socklen_t address_length);

OUTPUT_DEFINE(socketListen, nullptr_t)
socketListenOutput socketListen(const Socket* socket, int backlog_size);

OUTPUT_DEFINE(socketAccept, Socket)
socketAcceptOutput socketAccept(const Socket* socket, sockaddr* address, socklen_t* address_length);

OUTPUT_DEFINE(socketShutdown, nullptr_t)
socketShutdownOutput socketShutdown(const Socket* socket, int how);

OUTPUT_DEFINE(socketClose, nullptr_t)
socketCloseOutput socketClose(const Socket* socket);