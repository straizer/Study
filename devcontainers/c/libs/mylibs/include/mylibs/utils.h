#pragma once

#if defined(__clang__) || defined(__GNUC__)
#define ALIGNED_16 __attribute__((aligned(16)))
#else
#define ALIGNED_16
#endif

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_DEFINE(Name, T)                                                                        \
    typedef struct ALIGNED_16 {                                                                       \
        bool ok;                                                                                      \
        union {                                                                                       \
            T value;                                                                                  \
            const char* const error;                                                                  \
        } u;                                                                                          \
    } Name##Output;                                                                                   \
                                                                                                      \
    static inline Name##Output Name##Ok(T val) { return (Name##Output){.ok = true, .u.value = val}; } \
                                                                                                      \
    static inline Name##Output Name##Err(const char* const err) { return (Name##Output){.ok = false, .u.error = err}; }

const char* prefixErrno(const char* prefix);
const char* prefixError(const char* prefix, const char* message);