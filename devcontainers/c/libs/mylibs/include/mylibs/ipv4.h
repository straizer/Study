#pragma once

#include <stddef.h>

#include <netinet/in.h>

#include "./macros.h"

typedef struct in_addr in_addr;
typedef struct sockaddr_in sockaddr_in;

enum {
    IPV4_IP_PORT_BUFFER_SIZE = INET_ADDRSTRLEN + 6,  // IPv4 + ":" + PORT
};

OUTPUT_DEFINE(ipv4StringToAddress, in_addr)
ipv4StringToAddressOutput ipv4StringToAddress(const char* ip);

OUTPUT_DEFINE(ipv4SocketAddressToString, nullptr_t)
ipv4SocketAddressToStringOutput ipv4SocketAddressToString(const sockaddr_in* socket_address, char* out,
                                                          size_t out_size);

OUTPUT_DEFINE_ALIGNED(ipv4CreateSocketAddress, sockaddr_in, 32)
ipv4CreateSocketAddressOutput ipv4CreateSocketAddress(const in_addr* ipv4_address, in_port_t port);