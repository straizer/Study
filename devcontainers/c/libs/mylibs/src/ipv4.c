#include "../include/mylibs/ipv4.h"

#include <stdio.h>

#include <arpa/inet.h>

#include "../include/mylibs/errors.h"
#include "../include/mylibs/network.h"
#include "../include/mylibs/string.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    IP_BUFFER_SIZE = 16,
    SOCKET_ADDRESS_BUFFER_SIZE = 22,
};

OUTPUT_DEFINE(getTCPSocket, int)
static getTCPSocketOutput getTCPSocket(void);

static sockaddr_in getInternetSocketAddress(in_addr internet_address, in_port_t port);

static const char* closeSocketAndGetError(int socket, const char* original_error);

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

OUTPUT_CONSTRUCTORS(startTCPServer, int)
startTCPServerOutput startTCPServer(const in_port_t server_port, const int backlog_size) {
    if (backlog_size < 1) {
        return startTCPServerErr("backlog size is not positive");
    }

    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return startTCPServerErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    // Create a server address struct
    // - INADDR_ANY → 0.0.0.0 (listen on all interfaces)
    in_addr server_address = {0};
    server_address.s_addr = htonl(INADDR_ANY);
    const sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);

    // Assign IP + port to the socket
    if (bind(tcp_socket.u.value, (const sockaddr*)&server_socket_address, sizeof(server_socket_address)) == -1) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, prefixErrno("bind")));
    }

    // Put the socket into a passive (listening) mode, allowing it to accept incoming connection requests
    if (listen(tcp_socket.u.value, backlog_size) == -1) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, prefixErrno("listen")));
    }

    return startTCPServerOk(tcp_socket.u.value);
}

OUTPUT_CONSTRUCTORS(connectToServerViaTCP, int)
connectToServerViaTCPOutput connectToServerViaTCP(const in_addr server_address, const in_port_t server_port) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return connectToServerViaTCPErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    const sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);

    const connectToSocketOutput connect_result =
        connectToSocket(tcp_socket.u.value, (const sockaddr*)&server_socket_address, sizeof(server_socket_address));
    if (!connect_result.ok) {
        const char* const error = prefixError("connectToSocket", connect_result.u.error);
        return connectToServerViaTCPErr(closeSocketAndGetError(tcp_socket.u.value, error));
    }

    return connectToServerViaTCPOk(tcp_socket.u.value);
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

OUTPUT_CONSTRUCTORS(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(const int via_socket, const int how) {
    if (via_socket < 0) {
        return closeConnectionErr("socket file descriptor is negative");
    }
    if (how != SHUT_RD && how != SHUT_WR && how != SHUT_RDWR) {
        return closeConnectionErr("invalid shutdown mode");
    }

    if (shutdown(via_socket, how) == -1) {
        return closeConnectionErr(prefixErrno("shutdown"));
    }

    const closeFileDescriptorOutput result = closeFileDescriptor(via_socket);
    if (!result.ok) {
        return closeConnectionErr(prefixError("closeFileDescriptor", result.u.error));
    }

    return closeConnectionOk(nullptr);
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(getTCPSocket, int)
static getTCPSocketOutput getTCPSocket(void) {
    const int result = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (result == -1) {
        return getTCPSocketErr(prefixErrno("socket"));
    }

    return getTCPSocketOk(result);
}

static sockaddr_in getInternetSocketAddress(const in_addr internet_address, const in_port_t port) {
    sockaddr_in socket_address = {0};
    socket_address.sin_family = AF_INET;
    socket_address.sin_addr = internet_address;
    socket_address.sin_port = htons(port);
    return socket_address;
}

static const char* closeSocketAndGetError(const int socket, const char* const original_error) {
    const closeFileDescriptorOutput result = closeFileDescriptor(socket);
    if (!result.ok) {
        return errorDuring("closeFileDescriptor", result.u.error, original_error);
    }

    return original_error;
}