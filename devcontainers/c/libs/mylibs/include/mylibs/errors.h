#pragma once

enum {
    ERROR_BUFFER_SIZE = 256,
};

char* getErrorBuffer(void);

const char* prefixErrno(const char* prefix);
const char* prefixError(const char* prefix, const char* message);
const char* errorDuring(const char* primary_prefix, const char* primary_message, const char* secondary);
