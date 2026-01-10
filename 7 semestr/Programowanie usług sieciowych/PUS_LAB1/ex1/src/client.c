// cppcheck-suppress-file misra-c2012-21.8

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#include <mylibs/inet.h>
#include <mylibs/ipv4.h>
#include <mylibs/tcp.h>

#include "io.h"

enum {
    MESSAGE_BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 3) {
        printError("Invocation: %s <IPv4 ADDRESS> <PORT>", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getInternetAddressOutput server_address = getInternetAddress(argv[1]);
    if (!server_address.ok) {
        printError("getPort: %s", server_address.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getPortOutput server_port = getPort(argv[2]);
    if (!server_port.ok) {
        printError("getPort: %s", server_port.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const connectToServerViaTCPOutput client_socket =
        connectToServerViaTCP(server_address.u.value, server_port.u.value);
    if (!client_socket.ok) {
        printError("connectToServerViaTCP: %s", client_socket.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("After the three-way handshake. Waiting for server response");

    char message_buffer[MESSAGE_BUFFER_SIZE] = {0};
    const ssize_t bytes_read = read(client_socket.u.value, message_buffer, sizeof(message_buffer));
    if (bytes_read == -1) {
        perror("read");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (bytes_read == 0) {
        printError("Unexpected termination of connection by server (received FIN).");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("Received server response: %s", message_buffer);

    // Send a FIN to close the server connection
    printOutput("Shutting down server connection (sending FIN)");
    const closeConnectionOutput output = closeConnection(client_socket.u.value, SHUT_WR);
    if (!output.ok) {
        printError("closeConnection: %s", output.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("Terminating client");
    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
