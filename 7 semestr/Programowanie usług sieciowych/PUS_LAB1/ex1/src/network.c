#include "network.h"

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

enum {
    MAX_PORT_NUMBER = 65535U,
};

getPortOutput getPort(const char* const port_string) {
    if (port_string == nullptr) {
        return getPortErr("port string is NULL");
    }

    char* end = nullptr;
    errno = 0;
    const uint64_t port_raw = strtoul(port_string, &end, 10);

    if (errno != 0) {
        return getPortErr(prefixErrno("strtoul"));
    }

    if (end == port_string) {
        return getPortErr("port is not a number");
    }

    if (*end != '\0') {
        return getPortErr("trailing characters after port number");
    }

    if (port_raw > MAX_PORT_NUMBER) {
        return getPortErr("port number out of range (0..65535)");
    }

    return getPortOk((in_port_t)port_raw);
}

void connectToSocket(const int32_t via_socket, const sockaddr_in to_address) {
    if (connect(via_socket, (const struct sockaddr*)&to_address, sizeof(to_address)) == -1) {
        perror("connect()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
}

void closeConnection(const int32_t via_socket) {
    if (shutdown(via_socket, SHUT_WR) == -1) {
        perror("shutdown()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (close(via_socket) == -1) {
        perror("close()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
}