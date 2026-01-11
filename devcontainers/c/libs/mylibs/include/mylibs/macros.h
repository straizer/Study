#pragma once

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_DEFINE_ALIGN(Name, T, Alignment)          \
    typedef struct __attribute__((aligned(Alignment))) { \
        bool ok;                                         \
        union {                                          \
            T value;                                     \
            const char* const error;                     \
        } u;                                             \
    } Name##Output;

#define OUTPUT_DEFINE(Name, T) OUTPUT_DEFINE_ALIGN(Name, T, 16)

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_CONSTRUCTORS(Name, T)                                                                  \
    static inline Name##Output Name##Ok(T val) { return (Name##Output){.ok = true, .u.value = val}; } \
    static inline Name##Output Name##Err(const char* const err) { return (Name##Output){.ok = false, .u.error = err}; }
