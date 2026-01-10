#include "../include/mylibs/ipv4.h"

#include <stdio.h>

#include <arpa/inet.h>

#include "./errors.h"
#include "./string.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    IP_BUFFER_SIZE = 16,
    SOCKET_ADDRESS_BUFFER_SIZE = 22,
};

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(getInternetAddress, in_addr)
getInternetAddressOutput getInternetAddress(const char* const ip) {
    if (!stringIsValid(ip)) {
        return getInternetAddressErr("IP string is invalid");
    }

    in_addr address;

    const int result = inet_pton(AF_INET, ip, &address);
    if (result == -1) {
        return getInternetAddressErr(prefixErrno("inet_pton"));
    }
    if (result == 0) {
        return getInternetAddressErr(prefixError("inet_pton", "invalid network address"));
    }

    return getInternetAddressOk(address);
}

OUTPUT_CONSTRUCTORS(socketAddressToString, nullptr_t)
socketAddressToStringOutput socketAddressToString(const sockaddr_in socket_address, char* const out,
                                                  const size_t out_size) {
    if (out == nullptr) {
        return socketAddressToStringErr("out is NULL");
    }
    if (out_size < SOCKET_ADDRESS_BUFFER_SIZE) {
        return socketAddressToStringErr("output buffer too small (must be at least 22)");
    }

    char ip[IP_BUFFER_SIZE];
    if (inet_ntop(AF_INET, &socket_address.sin_addr, ip, sizeof(ip)) == nullptr) {
        return socketAddressToStringErr(prefixErrno("inet_ntop"));
    }

    const int result = snprintf(out, out_size, "%s:%u", ip, (unsigned)ntohs(socket_address.sin_port));
    if (result < 0) {
        return socketAddressToStringErr(prefixError("snprintf", "formatting failed"));
    }
    if ((size_t)result >= out_size) {
        return socketAddressToStringErr(prefixError("snprintf", "output truncated"));
    }

    return socketAddressToStringOk(nullptr);
}

sockaddr_in getInternetSocketAddress(const in_addr internet_address, const in_port_t port) {
    sockaddr_in socket_address = {0};
    socket_address.sin_family = AF_INET;
    socket_address.sin_addr = internet_address;
    socket_address.sin_port = htons(port);
    return socket_address;
}