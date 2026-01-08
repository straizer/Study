#include "ipv4.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>
#include <mylibs/network.h>

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    BUFFER_SIZE = 64,
};

OUTPUT_DEFINE(getTCPSocket, int32_t)
getTCPSocketOutput getTCPSocket(void);

sockaddr_in getInternetSocketAddress(in_addr internet_address, in_port_t port);

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

OUTPUT_CONSTRUCTORS(startTCPServer, int32_t)
startTCPServerOutput startTCPServer(const in_port_t server_port, const int32_t backlog_size) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return startTCPServerErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    // Create a server address struct
    // - INADDR_ANY → 0.0.0.0 (listen on all interfaces)
    in_addr server_address = {0};
    server_address.s_addr = htonl(INADDR_ANY);
    sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);

    // Assign IP + port to the socket
    if (bind(tcp_socket.u.value, (struct sockaddr*)&server_socket_address, sizeof(server_socket_address)) == -1) {
        const char* const error = prefixErrno("bind");
        const closeConnectionOutput result = closeConnection(tcp_socket.u.value, SHUT_RDWR);
        if (!result.ok) {
            return startTCPServerErr(errorDuring("closeConnection", result.u.error, error));
        }
        return startTCPServerErr(error);
    }

    // Put the socket into a passive (listening) mode, allowing it to accept incoming connection requests
    if (listen(tcp_socket.u.value, backlog_size) == -1) {
        const char* const error = prefixErrno("listen");
        const closeConnectionOutput result = closeConnection(tcp_socket.u.value, SHUT_RDWR);
        if (!result.ok) {
            return startTCPServerErr(errorDuring("closeConnection", result.u.error, error));
        }
        return startTCPServerErr(error);
    }

    return startTCPServerOk(tcp_socket.u.value);
}

int32_t connectToServerViaTCP(const in_addr server_address, const in_port_t server_port) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        (void)fprintf(stderr, "getTCPSocket: %s\n", tcp_socket.u.error);
        exit(EXIT_FAILURE);  // cppcheck-suppress misra-c2012-21.8 // NOLINT(concurrency-mt-unsafe)
    }

    const sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);
    const connectToSocketOutput result = connectToSocket(tcp_socket.u.value, server_socket_address);
    if (!result.ok) {
        (void)fprintf(stderr, "connectToSocket: %s\n", result.u.error);
        exit(EXIT_FAILURE);  // cppcheck-suppress misra-c2012-21.8 // NOLINT(concurrency-mt-unsafe)
    }
    return tcp_socket.u.value;
}

void socketAddressToString(const sockaddr_in socket_address, char* const out) {
    char result[BUFFER_SIZE];
    if (inet_ntop(AF_INET, &socket_address.sin_addr, result, BUFFER_SIZE) == NULL) {
        perror("inet_ntop()");
        exit(EXIT_FAILURE);  // cppcheck-suppress misra-c2012-21.8 // NOLINT(concurrency-mt-unsafe)
    }
    const uint64_t ip_length = strlen(result);
    (void)snprintf(&result[ip_length], BUFFER_SIZE - ip_length, ":%d", ntohs(socket_address.sin_port));
    (void)memcpy(out, result, strlen(result) + 1U);
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(getTCPSocket, int32_t)
getTCPSocketOutput getTCPSocket(void) {
    const int32_t result = socket(AF_INET, SOCK_STREAM, PF_UNSPEC);
    if (result == -1) {
        return getTCPSocketErr(prefixErrno("socket"));
    }

    return getTCPSocketOk(result);
}

sockaddr_in getInternetSocketAddress(const in_addr internet_address, const in_port_t port) {
    sockaddr_in socket_address = {0};
    socket_address.sin_family = AF_INET;
    socket_address.sin_addr = internet_address;
    socket_address.sin_port = htons(port);
    return socket_address;
}