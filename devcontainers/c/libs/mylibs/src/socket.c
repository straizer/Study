#include "../include/mylibs/socket.h"

#include "../include/mylibs/errors.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(connectToSocket, nullptr_t)
connectToSocketOutput connectToSocket(const int via_socket, const sockaddr* const to_address,
                                      const socklen_t to_address_len) {
    if (via_socket < 0) {
        return connectToSocketErr("socket file descriptor is negative");
    }
    if (to_address == nullptr) {
        return connectToSocketErr("address is NULL");
    }
    if (to_address_len < sizeof(sockaddr)) {
        return connectToSocketErr("address length too small");
    }

    if (connect(via_socket, to_address, to_address_len) == -1) {
        return connectToSocketErr(prefixErrno("connect"));
    }

    return connectToSocketOk(nullptr);
}