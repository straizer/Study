#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include "ipv4.h"
#include "network.h"

enum {
    BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 3) {
        (void)fprintf(stderr, "Invocation: %s <IPv4 ADDRESS> <PORT>\n", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    const in_addr server_address = getInternetAddress(argv[1]);
    const in_port_t server_port = getPort(argv[2]);

    const int32_t client_socket = connectToServerViaTCP(server_address, server_port);
    printf("After the three-way handshake. Waiting for server response\n");

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

    printf("Received server response: %s\n", buffer);

    // Send a FIN to close the server connection
    printf("Shutting down server connection (sending FIN)\n");
    closeConnection(client_socket);

    printf("Terminating client\n");
    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
