#include "../include/mylibs/network.h"

#include <errno.h>
#include <stdlib.h>

#include "../include/mylibs/errors.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    MAX_PORT_NUMBER = 65535U,
};

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(getPort, uint16_t)
getPortOutput getPort(const char* const port_string) {
    if (port_string == nullptr) {
        return getPortErr("port string is NULL");
    }

    char* end = nullptr;
    errno = 0;

    const uint64_t port = strtoul(port_string, &end, 10);
    if (errno != 0) {
        return getPortErr(prefixErrno("strtoul"));
    }
    if (end == port_string) {
        return getPortErr("port is not a number");
    }
    if (*end != '\0') {
        return getPortErr("trailing characters after port number");
    }

    if (port > MAX_PORT_NUMBER) {
        return getPortErr("port number out of range (0..65535)");
    }

    return getPortOk((uint16_t)port);
}

OUTPUT_CONSTRUCTORS(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(const int32_t via_socket, const sockaddr* const to_address,
                                      const socklen_t to_address_len) {
    if (to_address == nullptr) {
        return connectToSocketErr("address is NULL");
    }
    if (to_address_len == 0U) {
        return connectToSocketErr("address length is 0");
    }

    if (connect(via_socket, to_address, to_address_len) == -1) {
        return connectToSocketErr(prefixErrno("connect"));
    }

    return connectToSocketOk(nullptr);
}