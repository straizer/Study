#pragma once

#include <netinet/in.h>

#include "./macros.h"
#include "./socket.h"

typedef struct in_addr in_addr;

enum {
    IPV4_IP_PORT_BUFFER_SIZE = INET_ADDRSTRLEN + 6,  // IPv4 + ":" + PORT
};

DECLARATION(ipv4StringToAddress, const in_addr, const char* ip)
DECLARATION_VOID(ipv4SocketAddressToString, const SocketAddress* address, char* out, size_t out_size)
DECLARATION_ALIGNED(ipv4CreateSocketAddress, SocketAddress, 128, const in_addr* ipv4_address, in_port_t port)