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

DEFINITION_RVALUE(socketCreate, Socket, const int domain, const int type, const int protocol) {
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

DEFINITION_NULL(socketConnect, const Socket* const socket, const SocketAddress* const address) {
    if (socket == nullptr) {
        return socketConnectErr("socket is NULL");
    }
    if (socket->file_descriptor < 0) {
        return socketConnectErr("socket file descriptor is negative");
    }

    if (address == nullptr) {
        return socketConnectErr("address is NULL");
    }
    if (address->length == 0U) {
        return socketConnectErr("address length is 0");
    }

    if (connect(socket->file_descriptor, (const struct sockaddr*)&address->storage, address->length) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_connect(buffer, ERROR_BUFFER_SIZE, socket->file_descriptor,
                                (const struct sockaddr*)&address->storage, (int)address->length);
        return socketConnectErr(buffer);
    }

    return socketConnectOk();
}

DEFINITION_NULL(socketBind, const Socket* const socket, const SocketAddress* const address) {
    if (socket == nullptr) {
        return socketBindErr("socket is NULL");
    }
    if (socket->file_descriptor < 0) {
        return socketBindErr("socket file descriptor is negative");
    }

    if (address == nullptr) {
        return socketBindErr("address is NULL");
    }
    if (address->length == 0U) {
        return socketBindErr("address length is 0");
    }

    if (bind(socket->file_descriptor, (const struct sockaddr*)&address->storage, address->length) == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_bind(buffer, ERROR_BUFFER_SIZE, socket->file_descriptor,
                             (const struct sockaddr*)&address->storage, (int)address->length);
        return socketBindErr(buffer);
    }
    return socketBindOk();
}

DEFINITION_NULL(socketListen, const Socket* const socket, const int backlog_size) {
    if (socket == nullptr) {
        return socketListenErr("socket is NULL");
    }
    if (socket->file_descriptor < 0) {
        return socketListenErr("socket file descriptor is negative");
    }

    if (backlog_size < 0) {
        return socketListenErr("backlog size is negative");
    }

#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wanalyzer-fd-leak"
    if (listen(socket->file_descriptor, backlog_size) == -1) {
#pragma GCC diagnostic pop
        char* const buffer = getErrorBuffer();
        explain_message_listen(buffer, ERROR_BUFFER_SIZE, socket->file_descriptor, backlog_size);
        return socketListenErr(buffer);
    }

    return socketListenOk();
}

DEFINITION_RVALUE(socketAccept, Socket, const Socket* const socket, SocketAddress* const address) {
    if (socket == nullptr) {
        return socketAcceptErr("socket is NULL");
    }
    if (socket->file_descriptor < 0) {
        return socketAcceptErr("socket file descriptor is negative");
    }

    struct sockaddr* socket_address = nullptr;
    socklen_t socket_length = 0;
    socklen_t* socket_length_ptr = nullptr;

    if (address != nullptr) {
        socket_address = (struct sockaddr*)&address->storage;
        socket_length = sizeof(address->storage);
        socket_length_ptr = &socket_length;
    }

    const int connected_socket_file_descriptor = accept(socket->file_descriptor, socket_address, socket_length_ptr);
    if (connected_socket_file_descriptor == -1) {
        char* const buffer = getErrorBuffer();
        explain_message_accept(buffer, ERROR_BUFFER_SIZE, socket->file_descriptor, socket_address, socket_length_ptr);
        return socketAcceptErr(buffer);
    }

    if (address != nullptr) {
        address->length = socket_length;
    }

    return socketAcceptOk(socketConstructor(connected_socket_file_descriptor));
}

DEFINITION_NULL(socketShutdown, const Socket* const socket, const int how) {
    if (socket == nullptr) {
        return socketShutdownErr("socket is NULL");
    }
    if (socket->file_descriptor < 0) {
        return socketShutdownErr("socket file descriptor is negative");
    }

    if (how != SHUT_RD && how != SHUT_WR && how != SHUT_RDWR) {
        return socketShutdownErr("invalid shutdown mode");
    }

    if (shutdown(socket->file_descriptor, how) == -1) {
        return socketShutdownErr(prefixErrno("shutdown"));
    }

    return socketShutdownOk();
}

DEFINITION_NULL(socketClose, Socket* socket) {
    if (socket == nullptr) {
        return socketCloseErr("socket is NULL");
    }

    const closeFileDescriptorOutput output = closeFileDescriptor(&socket->file_descriptor);
    if (!output.ok) {
        return socketCloseErr(prefixError("closeFileDescriptor", output.u.error));
    }

    return socketCloseOk();
}

/* ------------------------------------------ Private function definitions ------------------------------------------ */

static Socket socketConstructor(const int file_descriptor) { return (Socket){.file_descriptor = file_descriptor}; }