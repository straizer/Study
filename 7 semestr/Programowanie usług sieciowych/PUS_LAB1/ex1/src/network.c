#include "network.h"

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

enum {
    MAX_PORT_NUMBER = 65535,
};

in_port_t getPort(const char* const port_string) {
    char* end = nullptr;
    const uint64_t port_raw = strtoul(port_string, &end, 10);
    if (*end != '\0') {
        (void)fprintf(stderr, "Missing trailing \\0");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (port_raw > MAX_PORT_NUMBER) {
        (void)fprintf(stderr, "Invalid port number: %lu\n", port_raw);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    return (uint16_t)port_raw;
}

void connectToSocket(const int32_t via_socket, const sockaddr_in to_address) {
    if (connect(via_socket, (const sockaddr*)&to_address, sizeof(to_address)) == -1) {
        perror("connect()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
}

void closeConnection(const int32_t via_socket) {
    if (shutdown(via_socket, SHUT_WR) == -1) {
        perror("shutdown()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    close(via_socket);
}