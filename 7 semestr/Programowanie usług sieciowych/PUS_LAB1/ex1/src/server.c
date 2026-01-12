// cppcheck-suppress-file misra-c2012-21.8

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#include <mylibs/inet.h>
#include <mylibs/ipv4.h>
#include <mylibs/socket.h>
#include <mylibs/tcp.h>

#include "io.h"

enum {
    MESSAGE_BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 2) {
        printError("Invocation: %s <PORT>", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getPortOutput server_port = getPort(argv[1]);
    if (!server_port.ok) {
        printError("getPort: %s", server_port.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    startTCPServerOutput server_socket = startTCPServer(server_port.u.value, 2);
    if (!server_socket.ok) {
        printError("getPort: %s", server_socket.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("Server is listening for incoming connection");

    // Create a client address struct
    SocketAddress client_address = {.length = sizeof(struct sockaddr_in)};

    // Wait for the incoming connection and return a new socket file descriptor for communicating with a client
    const socketAcceptOutput client_socket = socketAccept(&server_socket.u.value, &client_address);
    if (!client_socket.ok) {
        printError("socketAccept: %s", client_socket.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    char client_address_buffer[IPV4_IP_PORT_BUFFER_SIZE];
    const ipv4SocketAddressToStringOutput output =
        ipv4SocketAddressToString(&client_address, client_address_buffer, IPV4_IP_PORT_BUFFER_SIZE);
    if (!output.ok) {
        printError("ipv4SocketAddressToString: %s", output.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("TCP connection accepted from %s", client_address_buffer);
    printOutput("Sending current date and time");

    // Get the current system time and convert it to a human-readable local time structure
    time_t raw_time = 0;
    (void)time(&raw_time);
    struct tm time_info;
    (void)localtime_r(&raw_time, &time_info);

    // Format the time information into a text string
    char message_buffer[MESSAGE_BUFFER_SIZE];
    if (strftime(message_buffer, sizeof(message_buffer), "%Y-%m-%d %H:%M:%S %Z", &time_info) == 0U) {
        printError("Buffer size exceeded");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Sends the buffer to the connected client through the client socket
    if (write(client_socket.u.value.file_descriptor, message_buffer, strlen(message_buffer)) == -1) {
        perror("write");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // If read returns 0, the client has closed the connection (sent FIN)
    const ssize_t bytes_read = read(client_socket.u.value.file_descriptor, message_buffer, sizeof(message_buffer));
    if (bytes_read == -1) {
        perror("read");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (bytes_read > 0) {
        printError("Unexpected bytes received from %s:%d", client_address_buffer,
                   ntohs(((const struct sockaddr_in*)&client_address.value)->sin_port));
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printOutput("Connection terminated by the client (received FIN, entering CLOSE_WAIT state)");

    // Send a FIN to close the client connection
    printOutput("Shutting down client connection (sending FIN)");
    const closeConnectionOutput close_client_socket_output = closeConnection(client_socket.u.value, SHUT_WR);
    if (!close_client_socket_output.ok) {
        printError("closeConnection: %s", close_client_socket_output.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Close listening socket
    printOutput("Closing listening socket and terminating server");
    const socketCloseOutput close_server_socket_output = socketClose(&server_socket.u.value);
    if (!close_server_socket_output.ok) {
        printError("socketClose: %s", close_server_socket_output.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
