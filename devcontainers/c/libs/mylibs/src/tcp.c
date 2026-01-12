#include "../include/mylibs/tcp.h"

#include "../include/mylibs/ipv4.h"
#include "./errors.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

DECLARATION_NO_PARAMS_STATIC(getTCPSocket, const Socket)

static const char* closeSocketAndGetError(Socket socket, const char* prefix, const char* error);

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION(startTCPServer, Socket, const in_port_t server_port, const int backlog_size) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return startTCPServerErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    // Create a server address struct
    // - INADDR_ANY → 0.0.0.0 (listen on all interfaces)
    in_addr server_address = {0};
    server_address.s_addr = htonl(INADDR_ANY);
    const ipv4CreateSocketAddressOutput server_socket_address = ipv4CreateSocketAddress(&server_address, server_port);
    if (!server_socket_address.ok) {
        return startTCPServerErr(
            closeSocketAndGetError(tcp_socket.u.value, "ipv4CreateSocketAddress", server_socket_address.u.error));
    }

    const SocketAddress ssa = {.value = (const sockaddr*)&server_socket_address.u.value,
                               .length = sizeof(server_socket_address.u.value)};

    // Assign IP + port to the socket
    const socketBindOutput bind_output = socketBind(&tcp_socket.u.value, &ssa);
    if (!bind_output.ok) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, "socketBind", bind_output.u.error));
    }

    // Put the socket into a passive (listening) mode, allowing it to accept incoming connection requests
    const socketListenOutput listen_output = socketListen(&tcp_socket.u.value, backlog_size);
    if (!listen_output.ok) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, "socketListen", listen_output.u.error));
    }

    return startTCPServerOk(tcp_socket.u.value);
}

DEFINITION(connectToServerViaTCP, Socket, const in_addr server_address, const in_port_t server_port) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return connectToServerViaTCPErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    const ipv4CreateSocketAddressOutput server_socket_address = ipv4CreateSocketAddress(&server_address, server_port);
    if (!server_socket_address.ok) {
        return connectToServerViaTCPErr(
            closeSocketAndGetError(tcp_socket.u.value, "ipv4CreateSocketAddress", server_socket_address.u.error));
    }

    const SocketAddress ssa = {.value = (const sockaddr*)&server_socket_address.u.value,
                               .length = sizeof(server_socket_address.u.value)};

    const socketConnectOutput connect_output = socketConnect(&tcp_socket.u.value, &ssa);
    if (!connect_output.ok) {
        return connectToServerViaTCPErr(
            closeSocketAndGetError(tcp_socket.u.value, "socketConnect", connect_output.u.error));
    }

    return connectToServerViaTCPOk(tcp_socket.u.value);
}

DEFINITION_VOID(closeConnection, const Socket socket, const int how) {
    const socketShutdownOutput shutdown_output = socketShutdown(&socket, how);
    if (!shutdown_output.ok) {
        return closeConnectionErr(prefixError("socketShutdown", shutdown_output.u.error));
    }

    const socketCloseOutput close_output = socketClose(&socket);
    if (!close_output.ok) {
        return closeConnectionErr(prefixError("socketClose", close_output.u.error));
    }

    return closeConnectionOk();
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

DEFINITION_NO_PARAMS_STATIC(getTCPSocket, Socket) {
    const socketCreateOutput output = socketCreate(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (!output.ok) {
        return getTCPSocketErr(prefixError("socketCreate", output.u.error));
    }

    return getTCPSocketOk(output.u.value);
}

static const char* closeSocketAndGetError(const Socket socket, const char* const prefix, const char* const error) {
    const socketCloseOutput output = socketClose(&socket);
    if (!output.ok) {
        return errorDuring("socketClose", output.u.error, prefix, error);
    }

    return prefixError(prefix, error);
}