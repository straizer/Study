#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <unistd.h>

#include "ipv4.h"
#include "network.h"

enum {
    BUFFER_SIZE = 256,
};

int main(const int argc, const char* const* const argv) {
    if (argc != 2) {
        (void)fprintf(stderr, "Invocation: %s <PORT>\n", argv[0]);
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    const in_port_t server_port = getPort(argv[1]);

    const int32_t server_socket = startTCPServer(server_port, 2);
    printf("Server is listening for incoming connection\n");

    // Create a client address struct
    struct sockaddr_in client_address = {0};
    socklen_t client_address_length = sizeof(client_address);

    // Wait for the incoming connection and return a new socket file descriptor for communicating with a client
    const int32_t client_socket = accept(server_socket, (struct sockaddr*)&client_address, &client_address_length);
    if (client_socket == -1) {
        perror("accept()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    char client_ip[BUFFER_SIZE];
    socketAddressToString(client_address, client_ip);
    printf("TCP connection accepted from %s\n", client_ip);

    printf("Sending current date and time\n");

    // Get the current system time and convert it to a human-readable local time structure
    time_t raw_time = 0;
    (void)time(&raw_time);
    struct tm time_info;
    (void)localtime_r(&raw_time, &time_info);

    // Format the time information into a text string
    char buffer[BUFFER_SIZE];
    if (strftime(buffer, sizeof(buffer), "%Y-%m-%d %H:%M:%S %Z", &time_info) == 0) {
        (void)fprintf(stderr, "Buffer size exceeded");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // Sends the buffer to the connected client through the client socket
    if (write(client_socket, buffer, strlen(buffer)) == -1) {
        perror("write()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    // If read returns 0, the client has closed the connection (sent FIN)
    const ssize_t bytes_read = read(client_socket, buffer, sizeof(buffer));
    if (bytes_read == -1) {
        perror("read()");
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }
    if (bytes_read > 0) {
        (void)fprintf(stderr, "Unexpected bytes received from %s:%d\n", client_ip, ntohs(client_address.sin_port));
        exit(EXIT_FAILURE);  // NOLINT(concurrency-mt-unsafe)
    }

    printf("Connection terminated by the client (received FIN, entering CLOSE_WAIT state)\n");

    // Send a FIN to close the client connection
    printf("Shutting down client connection (sending FIN)\n");
    closeConnection(client_socket);

    // Close listening socket
    printf("Closing listening socket and terminating server\n");
    close(server_socket);

    exit(EXIT_SUCCESS);  // NOLINT(concurrency-mt-unsafe)
}
