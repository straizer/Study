#pragma once

#include <stddef.h>

#define STRUCT_ALIGNED(Alignment) struct __attribute__((aligned(Alignment)))

/* ------------------------------------------------- Private macros ------------------------------------------------- */

// Concatenate tokens
#define INTERNAL__CONCATENATE_TOKENS(a, b) a##b

// Declare function and output
#define INTERNAL__DECLARE_ALIGNED(Name, Static, T, Alignment, ...) \
    typedef STRUCT_ALIGNED(Alignment) {                            \
        bool ok;                                                   \
        union {                                                    \
            const T value;                                         \
            const char* const error;                               \
        } u;                                                       \
    }                                                              \
    INTERNAL__CONCATENATE_TOKENS(Name, Output);                    \
    Static INTERNAL__CONCATENATE_TOKENS(Name, Output) Name(__VA_ARGS__);

#define INTERNAL__DECLARE(Name, Static, T, ...) INTERNAL__DECLARE_ALIGNED(Name, Static, T, 16, __VA_ARGS__)

// Define output constructor
#define INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Suffix, Param, Ok, Assignment)                                 \
    static inline INTERNAL__CONCATENATE_TOKENS(Name, Output) INTERNAL__CONCATENATE_TOKENS(Name, Suffix)(Param) { \
        return (INTERNAL__CONCATENATE_TOKENS(Name, Output)){.ok = (Ok), .u.Assignment};                          \
    }

#define INTERNAL__DEFINE_OUTPUT_CONSTRUCTORS(Name, Param, Value)                              \
    INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Err, const char* const err, false, error = err) \
    INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Ok, Param, true, value = (Value))

/* ------------------------------------------------- Public macros ------------------------------------------------- */

#define DECLARATION(Name, T, ...) INTERNAL__DECLARE(Name, , T, __VA_ARGS__)
#define DECLARATION_VOID(Name, ...) INTERNAL__DECLARE(Name, , nullptr_t, __VA_ARGS__)
#define DECLARATION_NO_PARAMS_STATIC(Name, T) INTERNAL__DECLARE(Name, static, T, void)
#define DECLARATION_ALIGNED(Name, T, Alignment, ...) INTERNAL__DECLARE_ALIGNED(Name, , T, Alignment, __VA_ARGS__)

#define OUTPUT_CONSTRUCTORS(Name, T) INTERNAL__DEFINE_OUTPUT_CONSTRUCTORS(Name, const T val, val)
#define OUTPUT_CONSTRUCTORS_VOID(Name) INTERNAL__DEFINE_OUTPUT_CONSTRUCTORS(Name, void, nullptr)
