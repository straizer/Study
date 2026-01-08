// cppcheck-suppress-file misra-c2012-21.8

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include <mylibs/network.h>

#include "io.h"
#include "ipv4.h"

enum {
    BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 3) {
        (void)fprintf(stderr, "Invocation: %s <IPv4 ADDRESS> <PORT>\n", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getInternetAddressOutput server_address = getInternetAddress(argv[1]);
    if (!server_address.ok) {
        (void)fprintf(stderr, "getPort: %s\n", server_address.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getPortOutput server_port = getPort(argv[2]);
    if (!server_port.ok) {
        (void)fprintf(stderr, "getPort: %s\n", server_port.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const int32_t client_socket = connectToServerViaTCP(server_address.u.value, server_port.u.value);
    print("After the three-way handshake. Waiting for server response\n");

    char buffer[BUFFER_SIZE] = {0};
    const ssize_t bytes_read = read(client_socket, buffer, sizeof(buffer));
    if (bytes_read == -1) {
        perror("read()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (bytes_read == 0) {
        (void)fprintf(stderr, "Unexpected termination of connection by server (received FIN).\n");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    print("Received server response: %s\n", buffer);

    // Send a FIN to close the server connection
    print("Shutting down server connection (sending FIN)\n");
    const closeConnectionOutput result = closeConnection(client_socket, SHUT_WR);
    if (!result.ok) {
        (void)fprintf(stderr, "closeConnection: %s\n", result.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    print("Terminating client\n");
    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
