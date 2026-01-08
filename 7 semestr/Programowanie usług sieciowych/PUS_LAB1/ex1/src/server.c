// cppcheck-suppress-file misra-c2012-21.8

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#include <mylibs/network.h>

#include "io.h"
#include "ipv4.h"

enum {
    CLIENT_ADDRESS_BUFFER_SIZE = 22,
    MESSAGE_BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 2) {
        (void)fprintf(stderr, "Invocation: %s <PORT>\n", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const getPortOutput server_port = getPort(argv[1]);
    if (!server_port.ok) {
        (void)fprintf(stderr, "getPort: %s\n", server_port.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    const startTCPServerOutput server_socket = startTCPServer(server_port.u.value, 2);
    if (!server_socket.ok) {
        (void)fprintf(stderr, "getPort: %s\n", server_socket.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    print("Server is listening for incoming connection\n");

    // Create a client address struct
    sockaddr_in client_address = {0};
    socklen_t client_address_length = sizeof(client_address);

    // Wait for the incoming connection and return a new socket file descriptor for communicating with a client
    const int32_t client_socket =
        accept(server_socket.u.value, (struct sockaddr*)&client_address, &client_address_length);
    if (client_socket == -1) {
        perror("accept()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    char client_address_buffer[CLIENT_ADDRESS_BUFFER_SIZE];
    const socketAddressToStringOutput result =
        socketAddressToString(client_address, client_address_buffer, CLIENT_ADDRESS_BUFFER_SIZE);
    if (!result.ok) {
        (void)fprintf(stderr, "socketAddressToString: %s\n", result.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    print("TCP connection accepted from %s\n", client_address_buffer);
    print("Sending current date and time\n");

    // Get the current system time and convert it to a human-readable local time structure
    time_t raw_time = 0;
    (void)time(&raw_time);
    struct tm time_info;
    (void)localtime_r(&raw_time, &time_info);

    // Format the time information into a text string
    char message_buffer[MESSAGE_BUFFER_SIZE];
    if (strftime(message_buffer, sizeof(message_buffer), "%Y-%m-%d %H:%M:%S %Z", &time_info) == 0U) {
        (void)fprintf(stderr, "Buffer size exceeded");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Sends the buffer to the connected client through the client socket
    if (write(client_socket, message_buffer, strlen(message_buffer)) == -1) {
        perror("write()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // If read returns 0, the client has closed the connection (sent FIN)
    const ssize_t bytes_read = read(client_socket, message_buffer, sizeof(message_buffer));
    if (bytes_read == -1) {
        perror("read()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (bytes_read > 0) {
        (void)fprintf(stderr, "Unexpected bytes received from %s:%d\n", client_address_buffer,
                      ntohs(client_address.sin_port));
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    print("Connection terminated by the client (received FIN, entering CLOSE_WAIT state)\n");

    // Send a FIN to close the client connection
    print("Shutting down client connection (sending FIN)\n");
    const closeConnectionOutput close_client_socket_result = closeConnection(client_socket, SHUT_WR);
    if (!close_client_socket_result.ok) {
        (void)fprintf(stderr, "closeConnection: %s\n", close_client_socket_result.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Close listening socket
    print("Closing listening socket and terminating server\n");
    const closeConnectionOutput close_server_socket_result = closeConnection(server_socket.u.value, SHUT_RDWR);
    if (!close_server_socket_result.ok) {
        (void)fprintf(stderr, "closeConnection: %s\n", close_server_socket_result.u.error);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
