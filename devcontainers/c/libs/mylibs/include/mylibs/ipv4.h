#pragma once

#include <netinet/in.h>

#include "./macros.h"
#include "./socket.h"
#include "./string.h"

typedef struct in_addr in_addr;

DECLARATION(ipv4StringToAddress, const in_addr, const char* ip)
DECLARATION_ALIGNED(ipv4SocketAddressToString, String, 64, const SocketAddress* address)
DECLARATION_ALIGNED(ipv4CreateSocketAddress, SocketAddress, 128, const in_addr* ipv4_address, in_port_t port)