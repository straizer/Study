#pragma once

#include <sys/socket.h>

#include "utils.h"

typedef struct sockaddr sockaddr;

OUTPUT_DEFINE(socketCreate, int)
socketCreateOutput socketCreate(int domain, int type, int protocol);

OUTPUT_DEFINE(socketConnect, nullptr_t)
socketConnectOutput socketConnect(int socket, const sockaddr* address, socklen_t address_length);

OUTPUT_DEFINE(socketBind, nullptr_t)
socketBindOutput socketBind(int socket, const sockaddr* address, socklen_t address_length);

OUTPUT_DEFINE(socketListen, nullptr_t)
socketListenOutput socketListen(int socket, int backlog_size);

OUTPUT_DEFINE(socketAccept, int)
socketAcceptOutput socketAccept(int socket, sockaddr* address, socklen_t* address_length);

OUTPUT_DEFINE(socketShutdown, nullptr_t)
socketShutdownOutput socketShutdown(int socket, int how);