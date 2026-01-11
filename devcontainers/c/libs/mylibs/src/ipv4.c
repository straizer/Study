#include "../include/mylibs/ipv4.h"

#include <stdio.h>

#include <arpa/inet.h>

#include "./errors.h"
#include "./string.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(ipv4StringToAddress, in_addr)
ipv4StringToAddressOutput ipv4StringToAddress(const char* const ip) {
    if (!stringIsValid(ip)) {
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

    return ipv4StringToAddressOk(address);
}

OUTPUT_CONSTRUCTORS_VOID(ipv4SocketAddressToString)
ipv4SocketAddressToStringOutput ipv4SocketAddressToString(const sockaddr_in* const socket_address, char* const out,
                                                          const size_t out_size) {
    if (socket_address == nullptr) {
        return ipv4SocketAddressToStringErr("socket_address is NULL");
    }
    if (socket_address->sin_family != AF_INET) {
        return ipv4SocketAddressToStringErr("address is not IPv4");
    }
    if (out == nullptr) {
        return ipv4SocketAddressToStringErr("out is NULL");
    }
    if (out_size < IPV4_IP_PORT_BUFFER_SIZE) {
        return ipv4SocketAddressToStringErr("output buffer too small");
    }

    char ip[INET_ADDRSTRLEN];
    if (inet_ntop(AF_INET, &socket_address->sin_addr, ip, sizeof(ip)) == nullptr) {
        return ipv4SocketAddressToStringErr(prefixErrno("inet_ntop"));
    }

    const int result = snprintf(out, out_size, "%s:%u", ip, (unsigned)ntohs(socket_address->sin_port));
    if (result < 0) {
        return ipv4SocketAddressToStringErr(prefixError("snprintf", "formatting failed"));
    }
    if ((size_t)result >= out_size) {
        return ipv4SocketAddressToStringErr(prefixError("snprintf", "output truncated"));
    }

    return ipv4SocketAddressToStringOk();
}

OUTPUT_CONSTRUCTORS(ipv4CreateSocketAddress, sockaddr_in)
ipv4CreateSocketAddressOutput ipv4CreateSocketAddress(const in_addr* const ipv4_address, const in_port_t port) {
    if (ipv4_address == nullptr) {
        return ipv4CreateSocketAddressErr("ipv4_address is NULL");
    }

    sockaddr_in socket_address = {0};
    socket_address.sin_family = AF_INET;
    socket_address.sin_addr = *ipv4_address;
    socket_address.sin_port = htons(port);

    return ipv4CreateSocketAddressOk(socket_address);
}