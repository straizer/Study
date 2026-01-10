#include "../include/mylibs/socket.h"

#include "../include/mylibs/errors.h"

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(socketCreate, int)
socketCreateOutput socketCreate(const int domain, const int type, const int protocol) {
    if (protocol < 0) {
        return socketCreateErr("protocol is negative");
    }

    const int fd = socket(domain, type, protocol);
    if (fd == -1) {
        return socketCreateErr(prefixErrno("socket"));
    }
    return socketCreateOk(fd);
}

OUTPUT_CONSTRUCTORS(socketConnect, nullptr_t)
socketConnectOutput socketConnect(const int socket, const sockaddr* const address, const socklen_t address_length) {
    if (socket < 0) {
        return socketConnectErr("socket file descriptor is negative");
    }
    if (address == nullptr) {
        return socketConnectErr("address is NULL");
    }
    if (address_length == 0U) {
        return socketConnectErr("address length is 0");
    }

    if (connect(socket, address, address_length) == -1) {
        return socketConnectErr(prefixErrno("connect"));
    }

    return socketConnectOk(nullptr);
}

OUTPUT_CONSTRUCTORS(socketBind, nullptr_t)
socketBindOutput socketBind(const int socket, const sockaddr* const address, const socklen_t address_length) {
    if (socket < 0) {
        return socketBindErr("socket file descriptor is negative");
    }
    if (address == nullptr) {
        return socketBindErr("address is NULL");
    }
    if (address_length == 0U) {
        return socketBindErr("address length is 0");
    }

    if (bind(socket, address, address_length) == -1) {
        return socketBindErr(prefixErrno("bind"));
    }
    return socketBindOk(nullptr);
}

OUTPUT_CONSTRUCTORS(socketListen, nullptr_t)
socketListenOutput socketListen(const int socket, const int backlog_size) {
    if (socket < 0) {
        return socketListenErr("socket file descriptor is negative");
    }
    if (backlog_size < 0) {
        return socketListenErr("backlog size is negative");
    }

    if (listen(socket, backlog_size) == -1) {
        return socketListenErr(prefixErrno("listen"));
    }
    return socketListenOk(nullptr);
}

OUTPUT_CONSTRUCTORS(socketAccept, int)
socketAcceptOutput socketAccept(const int socket, sockaddr* const address, socklen_t* const address_length) {
    if (socket < 0) {
        return socketAcceptErr("socket file descriptor is negative");
    }

    /* POSIX: if addr is NULL, addrlen is ignored; passing NULL is fine. */
    if ((address == nullptr) != (address_length == nullptr)) {
        return socketAcceptErr("address and address length must be both NULL or both non-NULL");
    }

    const int connected_socket = accept(socket, address, address_length);
    if (connected_socket == -1) {
        return socketAcceptErr(prefixErrno("accept"));
    }

    return socketAcceptOk(connected_socket);
}

OUTPUT_CONSTRUCTORS(socketShutdown, nullptr_t)
socketShutdownOutput socketShutdown(const int socket, const int how) {
    if (socket < 0) {
        return socketShutdownErr("socket file descriptor is negative");
    }
    if (how != SHUT_RD && how != SHUT_WR && how != SHUT_RDWR) {
        return socketShutdownErr("invalid shutdown mode");
    }

    if (shutdown(socket, how) == -1) {
        return socketShutdownErr(prefixErrno("shutdown"));
    }

    return socketShutdownOk(nullptr);
}