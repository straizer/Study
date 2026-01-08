#include "../include/mylibs/network.h"

#include <errno.h>
#include <stdlib.h>
#include <unistd.h>

enum {
    MAX_PORT_NUMBER = 65535U,
};

OUTPUT_CONSTRUCTORS(getPort, in_port_t)
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

OUTPUT_CONSTRUCTORS(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(const int32_t via_socket, const sockaddr_in to_address) {
    if (connect(via_socket, (const struct sockaddr*)&to_address, sizeof(to_address)) == -1) {
        return connectToSocketErr(prefixErrno("connect"));
    }

    return connectToSocketOk(nullptr);
}

OUTPUT_CONSTRUCTORS(closeConnection, nullptr_t)
closeConnectionOutput closeConnection(const int32_t via_socket, const uint8_t how) {
    if (shutdown(via_socket, how) == -1) {
        return closeConnectionErr(prefixErrno("shutdown"));
    }

    if (close(via_socket) == -1) {
        return closeConnectionErr(prefixErrno("close"));
    }

    return closeConnectionOk(nullptr);
}