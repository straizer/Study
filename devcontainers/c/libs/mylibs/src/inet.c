#include "../include/mylibs/inet.h"

#include <errno.h>
#include <stdlib.h>

#include "./errors.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

enum {
    MAX_PORT_NUMBER = 65535,
};

/* ------------------------------------------ Public function definitions ------------------------------------------ */

DEFINITION_LRVALUE(getPort, uint16_t, const char* const port_string) {
    if (port_string == nullptr) {
        return getPortErr("port string is NULL");
    }

    char* end = nullptr;
    errno = 0;

    const unsigned long int port = strtoul(port_string, &end, 10);
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