#pragma once

#include <stddef.h>

#define CONCATENATE_TOKENS(a, b) a##b

#define STRUCT_ALIGNED(Alignment) struct __attribute__((aligned(Alignment)))

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_DEFINE_ALIGNED(Name, T, Alignment) \
    typedef STRUCT_ALIGNED(Alignment) {           \
        bool ok;                                  \
        union {                                   \
            const T value;                        \
            const char* const error;              \
        } u;                                      \
    }                                             \
    CONCATENATE_TOKENS(Name, Output);

#define OUTPUT_DEFINE(Name, T) OUTPUT_DEFINE_ALIGNED(Name, T, 16)
#define OUTPUT_DEFINE_VOID(Name) OUTPUT_DEFINE(Name, nullptr_t)

#define DECLARATION(Name, T, ...) \
    OUTPUT_DEFINE(Name, T)        \
    CONCATENATE_TOKENS(Name, Output) Name(__VA_ARGS__);

#define DECLARATION_STATIC(Name, T, ...) \
    OUTPUT_DEFINE(Name, T)               \
    static CONCATENATE_TOKENS(Name, Output) Name(__VA_ARGS__);

#define DECLARATION_VOID(Name, ...) \
    OUTPUT_DEFINE_VOID(Name)        \
    CONCATENATE_TOKENS(Name, Output) Name(__VA_ARGS__);

#define DECLARATION_ALIGNED(Name, T, Alignment, ...) \
    OUTPUT_DEFINE_ALIGNED(Name, T, Alignment)        \
    CONCATENATE_TOKENS(Name, Output) Name(__VA_ARGS__);

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_ERROR_CONSTRUCTOR(Name)                                                                    \
    static inline CONCATENATE_TOKENS(Name, Output) CONCATENATE_TOKENS(Name, Err)(const char* const err) { \
        return (CONCATENATE_TOKENS(Name, Output)){.ok = false, .u.error = err};                           \
    }

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_CONSTRUCTORS(Name, T)                                                           \
    OUTPUT_ERROR_CONSTRUCTOR(Name)                                                             \
    static inline CONCATENATE_TOKENS(Name, Output) CONCATENATE_TOKENS(Name, Ok)(const T val) { \
        return (CONCATENATE_TOKENS(Name, Output)){.ok = true, .u.value = val};                 \
    }

// cppcheck-suppress misra-c2012-20.7
#define OUTPUT_CONSTRUCTORS_VOID(Name)                                                  \
    OUTPUT_ERROR_CONSTRUCTOR(Name)                                                      \
    static inline CONCATENATE_TOKENS(Name, Output) CONCATENATE_TOKENS(Name, Ok)(void) { \
        return (CONCATENATE_TOKENS(Name, Output)){.ok = true, .u.value = nullptr};      \
    }
