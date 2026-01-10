#pragma once

#include <netinet/in.h>

#include "utils.h"

typedef struct in_addr in_addr;
typedef struct sockaddr_in sockaddr_in;

OUTPUT_DEFINE(getInternetAddress, in_addr)
getInternetAddressOutput getInternetAddress(const char* ip);

OUTPUT_DEFINE(socketAddressToString, nullptr_t)
socketAddressToStringOutput socketAddressToString(sockaddr_in socket_address, char* out, size_t out_size);

sockaddr_in getInternetSocketAddress(in_addr internet_address, in_port_t port);