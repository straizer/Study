#include "../include/mylibs/socket.h"

#include <libexplain/accept.h>
#include <libexplain/bind.h>
#include <libexplain/connect.h>
#include <libexplain/listen.h>
#include <libexplain/socket.h>

#include "./errors.h"
#include "./utils.h"

/* ------------------------------------------------ Private members ------------------------------------------------ */

static Socket socketConstructor(int file_descriptor);

/* ------------------------------------------ Public function definitions ------------------------------------------ */

OUTPUT_CONSTRUCTORS(socketCreate, Socket)
socketCreateOutput socketCreate(const int domain, const int type, const int protocol) {
    if (protocol < 0) {
        return socketCreateErr("protocol is negative");
    }

    const int file_descriptor = socket(domain, type, protocol);
    if (file_descriptor == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_socket(buffer, ERROR_BUFFER_SIZE, domain, type, protocol);
        return socketCreateErr(buffer);
    }

    return socketCreateOk(socketConstructor(file_descriptor));
}

OUTPUT_CONSTRUCTORS_VOID(socketConnect)
socketConnectOutput socketConnect(const Socket* const socket, const SocketAddress* const address) {
    if (socket == nullptr) {
        return socketConnectErr("socket is NULL");
    }
    if (address == nullptr) {
        return socketConnectErr("address is NULL");
    }

    if (socket->file_descriptor < 0) {
        return socketConnectErr("socket file descriptor is negative");
    }
    if (address->value == nullptr) {
        return socketConnectErr("address value is NULL");
    }
    if (address->length == 0U) {
        return socketConnectErr("address length is 0");
    }

    if (connect(socket->file_descriptor, address->value, address->length) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_connect(buffer, ERROR_BUFFER_SIZE, socket->file_descriptor, address->value,
                                (int)address->length);
        return socketConnectErr(buffer);
    }

    return socketConnectOk();
}

OUTPUT_CONSTRUCTORS_VOID(socketBind)
socketBindOutput socketBind(const Socket* const socket, const sockaddr* const address, const socklen_t address_length) {
    if (socket == nullptr) {
        return socketBindErr("socket is NULL");
    }
    const int file_descriptor = socket->file_descriptor;

    if (file_descriptor < 0) {
        return socketBindErr("socket file descriptor is negative");
    }
    if (address == nullptr) {
        return socketBindErr("address is NULL");
    }
    if (address_length == 0U) {
        return socketBindErr("address length is 0");
    }

    if (bind(file_descriptor, address, address_length) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_bind(buffer, ERROR_BUFFER_SIZE, file_descriptor, address, (int)address_length);
        return socketBindErr(buffer);
    }
    return socketBindOk();
}

OUTPUT_CONSTRUCTORS_VOID(socketListen)
socketListenOutput socketListen(const Socket* const socket, const int backlog_size) {
    if (socket == nullptr) {
        return socketListenErr("socket is NULL");
    }
    const int file_descriptor = socket->file_descriptor;

    if (file_descriptor < 0) {
        return socketListenErr("socket file descriptor is negative");
    }
    if (backlog_size < 0) {
        return socketListenErr("backlog size is negative");
    }

    if (listen(file_descriptor, backlog_size) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_listen(buffer, ERROR_BUFFER_SIZE, file_descriptor, backlog_size);
        return socketListenErr(buffer);
    }
    return socketListenOk();
}

OUTPUT_CONSTRUCTORS(socketAccept, Socket)
socketAcceptOutput socketAccept(const Socket* const socket, sockaddr* const address, socklen_t* const address_length) {
    if (socket == nullptr) {
        return socketAcceptErr("socket is NULL");
    }
    const int file_descriptor = socket->file_descriptor;

    if (file_descriptor < 0) {
        return socketAcceptErr("socket file descriptor is negative");
    }

    /* POSIX: if addr is NULL, addrlen is ignored; passing NULL is fine. */
    if ((address == nullptr) != (address_length == nullptr)) {
        return socketAcceptErr("address and address length must be both NULL or both non-NULL");
    }

    const int connected_socket_file_descriptor = accept(file_descriptor, address, address_length);
    if (connected_socket_file_descriptor == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_accept(buffer, ERROR_BUFFER_SIZE, file_descriptor, address, address_length);
        return socketAcceptErr(buffer);
    }

    return socketAcceptOk(socketConstructor(connected_socket_file_descriptor));
}

OUTPUT_CONSTRUCTORS_VOID(socketShutdown)
socketShutdownOutput socketShutdown(const Socket* const socket, const int how) {
    if (socket == nullptr) {
        return socketShutdownErr("socket is NULL");
    }
    const int file_descriptor = socket->file_descriptor;

    if (file_descriptor < 0) {
        return socketShutdownErr("socket file descriptor is negative");
    }
    if (how != SHUT_RD && how != SHUT_WR && how != SHUT_RDWR) {
        return socketShutdownErr("invalid shutdown mode");
    }

    if (shutdown(file_descriptor, how) == -1) {
        return socketShutdownErr(prefixErrno("shutdown"));
    }

    return socketShutdownOk();
}

OUTPUT_CONSTRUCTORS_VOID(socketClose)
socketCloseOutput socketClose(const Socket* socket) {
    if (socket == nullptr) {
        return socketCloseErr("socket is NULL");
    }

    const closeFileDescriptorOutput output = closeFileDescriptor(socket->file_descriptor);
    if (!output.ok) {
        return socketCloseErr(prefixError("closeFileDescriptor", output.u.error));
    }

    return socketCloseOk();
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

static Socket socketConstructor(const int file_descriptor) { return (Socket){.file_descriptor = file_descriptor}; }