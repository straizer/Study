#pragma once

#include <netinet/in.h>

#include "./macros.h"

typedef struct in_addr in_addr;
typedef struct sockaddr_in sockaddr_in;

enum {
    IPV4_IP_PORT_BUFFER_SIZE = INET_ADDRSTRLEN + 6,  // IPv4 + ":" + PORT
};

DECLARATION(ipv4StringToAddress, in_addr, const char* ip)
DECLARATION_VOID(ipv4SocketAddressToString, const sockaddr_in* socket_address, char* out, size_t out_size)
DECLARATION_ALIGNED(ipv4CreateSocketAddress, sockaddr_in, 32, const in_addr* ipv4_address, in_port_t port)