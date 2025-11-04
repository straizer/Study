#include "ipv4.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <arpa/inet.h>

#include "network.h"

enum {
    BUFFER_SIZE = 64,
};

int32_t getTCPSocket(void) {
    const int32_t result = socket(PF_INET, SOCK_STREAM, 0);
    if (result == -1) {
        perror("socket()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    return result;
}

struct in_addr getInternetAddress(const char* const ip) {
    struct in_addr address;
    const int result = inet_pton(AF_INET, ip, &address);
    if (result == 0) {
        (void)fprintf(stderr, "inet_pton(): invalid network address\n");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (result == -1) {
        perror("inet_pton()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    return address;
}

struct sockaddr_in getInternetSocketAddress(const struct in_addr internet_address, const in_port_t port) {
    struct sockaddr_in socket_address = {0};
    socket_address.sin_family = AF_INET;
    socket_address.sin_addr = internet_address;
    socket_address.sin_port = htons(port);
    return socket_address;
}

int32_t startTCPServer(const in_port_t server_port, const int32_t backlog_size) {
    const int32_t tcp_socket = getTCPSocket();

    // Create a server address struct
    // - AF_INET → IPv4
    // - INADDR_ANY → 0.0.0.0 (listen on all interfaces)
    struct in_addr server_address = {0};
    server_address.s_addr = htonl(INADDR_ANY);
    struct sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);

    // Assign IP + port to the socket
    if (bind(tcp_socket, (struct sockaddr*)&server_socket_address, sizeof(server_socket_address)) == -1) {
        perror("bind()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Put the socket into a passive (listening) mode, allowing it to accept incoming connection requests
    if (listen(tcp_socket, backlog_size) == -1) {
        perror("listen()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    return tcp_socket;
}

int32_t connectToServerViaTCP(const struct in_addr server_address, const in_port_t server_port) {
    const int32_t tcp_socket = getTCPSocket();
    const struct sockaddr_in server_socket_address = getInternetSocketAddress(server_address, server_port);
    connectToSocket(tcp_socket, server_socket_address);
    return tcp_socket;
}

void socketAddressToString(const struct sockaddr_in socket_addres, char* const out) {
    char result[BUFFER_SIZE];
    if (inet_ntop(AF_INET, &socket_addres.sin_addr, result, BUFFER_SIZE) == NULL) {
        perror("inet_ntop()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    const uint64_t ip_length = strlen(result);
    (void)snprintf(result + ip_length, BUFFER_SIZE - ip_length, ":%d", ntohs(socket_addres.sin_port));
    memcpy(out, result, strlen(result) + 1);
}