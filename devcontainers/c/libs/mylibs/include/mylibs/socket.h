#pragma once

#include <sys/socket.h>

#include "utils.h"

typedef struct sockaddr sockaddr;

OUTPUT_DEFINE(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(int via_socket, const sockaddr* to_address, socklen_t to_address_len);