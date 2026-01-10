#pragma once

const char* prefixErrno(const char* prefix);
const char* prefixError(const char* prefix, const char* message);
const char* errorDuring(const char* primary_prefix, const char* primary_message, const char* secondary);

bool stringIsValid(const char* string);
