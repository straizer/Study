#include "../include/mylibs/tcp.h"

#include "../include/mylibs/ipv4.h"
#include "../include/mylibs/socket.h"
#include "./errors.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

OUTPUT_DEFINE(getTCPSocket, int)
static getTCPSocketOutput getTCPSocket(void);

static const char* closeSocketAndGetError(int socket, const char* original_error);

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(startTCPServer, int)
startTCPServerOutput startTCPServer(const in_port_t server_port, const int backlog_size) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return startTCPServerErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    // Create a server address struct
    // - INADDR_ANY → 0.0.0.0 (listen on all interfaces)
    in_addr server_address = {0};
    server_address.s_addr = htonl(INADDR_ANY);
    const ipv4CreateSocketAddressOutput server_socket_address = ipv4CreateSocketAddress(&server_address, server_port);

    // Assign IP + port to the socket
    const socketBindOutput bind_output =
        socketBind(tcp_socket.u.value, (const sockaddr*)&server_socket_address.u.value, sizeof(server_socket_address));
    if (!bind_output.ok) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, bind_output.u.error));
    }

    // Put the socket into a passive (listening) mode, allowing it to accept incoming connection requests
    const socketListenOutput listen_output = socketListen(tcp_socket.u.value, backlog_size);
    if (!listen_output.ok) {
        return startTCPServerErr(closeSocketAndGetError(tcp_socket.u.value, listen_output.u.error));
    }

    return startTCPServerOk(tcp_socket.u.value);
}

OUTPUT_CONSTRUCTORS(connectToServerViaTCP, int)
connectToServerViaTCPOutput connectToServerViaTCP(const in_addr server_address, const in_port_t server_port) {
    const getTCPSocketOutput tcp_socket = getTCPSocket();
    if (!tcp_socket.ok) {
        return connectToServerViaTCPErr(prefixError("getTCPSocket", tcp_socket.u.error));
    }

    const ipv4CreateSocketAddressOutput server_socket_address = ipv4CreateSocketAddress(&server_address, server_port);

    const socketConnectOutput connect_output = socketConnect(
        tcp_socket.u.value, (const sockaddr*)&server_socket_address.u.value, sizeof(server_socket_address));
    if (!connect_output.ok) {
        const char* const error = prefixError("connectToSocket", connect_output.u.error);
        return connectToServerViaTCPErr(closeSocketAndGetError(tcp_socket.u.value, error));
    }

    return connectToServerViaTCPOk(tcp_socket.u.value);
}

OUTPUT_CONSTRUCTORS(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(const int via_socket, const int how) {
    const socketShutdownOutput shutdown_output = socketShutdown(via_socket, how);
    if (!shutdown_output.ok) {
        return closeConnectionErr(prefixError("socketShutdown", shutdown_output.u.error));
    }

    const closeFileDescriptorOutput close_output = closeFileDescriptor(via_socket);
    if (!close_output.ok) {
        return closeConnectionErr(prefixError("closeFileDescriptor", close_output.u.error));
    }

    return closeConnectionOk(nullptr);
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(getTCPSocket, int)
static getTCPSocketOutput getTCPSocket(void) {
    const socketCreateOutput output = socketCreate(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if (!output.ok) {
        return getTCPSocketErr(prefixError("socketCreate", output.u.error));
    }

    return getTCPSocketOk(output.u.value);
}

static const char* closeSocketAndGetError(const int socket, const char* const original_error) {
    const closeFileDescriptorOutput output = closeFileDescriptor(socket);
    if (!output.ok) {
        return errorDuring("closeFileDescriptor", output.u.error, original_error);
    }

    return original_error;
}