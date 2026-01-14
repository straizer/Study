#include "../include/mylibs/ipv4.h"

#include <stdio.h>

#include <arpa/inet.h>

#include "./buffer.h"
#include "./errors.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    IPV4_IP_PORT_BUFFER_SIZE = INET_ADDRSTRLEN + 6,  // IPv4 + ":" + PORT
};

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION_LVALUE(ipv4StringToAddress, in_addr, const char* const ip) {
    if (!bufferStringIsValid(ip)) {
        return ipv4StringToAddressErr("IP string is invalid");
    }

    in_addr address = {0};

    const int result = inet_pton(AF_INET, ip, &address);
    if (result == -1) {
        return ipv4StringToAddressErr(prefixErrno("inet_pton"));
    }

    if (result == 0) {
        return ipv4StringToAddressErr(prefixError("invalid IPv4 address", ip));
    }

    return ipv4StringToAddressOk(&address);
}

DEFINITION_LVALUE(ipv4SocketAddressToString, String, const SocketAddress* address) {
    if (address == nullptr) {
        return ipv4SocketAddressToStringErr("address is NULL");
    }
    const struct sockaddr_in* const socket_address = (const struct sockaddr_in* const)&address->storage;
    if (socket_address->sin_family != AF_INET) {
        return ipv4SocketAddressToStringErr("address is not IPv4");
    }

    char ip[INET_ADDRSTRLEN];
    if (inet_ntop(AF_INET, &socket_address->sin_addr, ip, sizeof(ip)) == nullptr) {
        return ipv4SocketAddressToStringErr(prefixErrno("inet_ntop"));
    }

    const stringFormatNewOutput string =
        stringFormatNew(IPV4_IP_PORT_BUFFER_SIZE, "%s:%u", ip, (unsigned)ntohs(socket_address->sin_port));
    if (!string.ok) {
        return ipv4SocketAddressToStringErr(prefixError("stringFormatNew", string.u.error));
    }

    return ipv4SocketAddressToStringOk(&string.u.value);
}

DEFINITION_LVALUE(ipv4CreateSocketAddress, SocketAddress, const in_addr* const ipv4_address, const in_port_t port) {
    if (ipv4_address == nullptr) {
        return ipv4CreateSocketAddressErr("ipv4_address is NULL");
    }

    SocketAddress address = {.length = sizeof(struct sockaddr_in)};
    struct sockaddr_in* const socket_address = (struct sockaddr_in* const)&address.storage;

    socket_address->sin_family = AF_INET;
    socket_address->sin_addr = *ipv4_address;
    socket_address->sin_port = htons(port);

    return ipv4CreateSocketAddressOk(&address);
}