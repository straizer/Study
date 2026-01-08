#pragma once

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_DEFINE(Name, T)                    \
    typedef struct __attribute__((aligned(16))) { \
        bool ok;                                  \
        union {                                   \
            T value;                              \
            const char* const error;              \
        } u;                                      \
    } Name##Output;

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_CONSTRUCTORS(Name, T)                                                                  \
    static inline Name##Output Name##Ok(T val) { return (Name##Output){.ok = true, .u.value = val}; } \
    static inline Name##Output Name##Err(const char* const err) { return (Name##Output){.ok = false, .u.error = err}; }

const char* prefixErrno(const char* prefix);
const char* prefixError(const char* prefix, const char* message);

bool stringIsValid(const char* string);