#pragma once

#include <stddef.h>

#define STRUCT_ALIGNED(Alignment) struct __attribute__((aligned(Alignment)))

/* ------------------------------------------------- Private macros ------------------------------------------------- */

// Concatenate tokens
#define INTERNAL__CONCATENATE_TOKENS(a, b) a##b
#define INTERNAL__OUTPUT_TOKEN(Name) INTERNAL__CONCATENATE_TOKENS(Name, Output)

// Function signature
#define INTERNAL__SIGNATURE(Name, Static, ...) Static INTERNAL__OUTPUT_TOKEN(Name) Name(__VA_ARGS__)

// Declare function and output
#define INTERNAL__DECLARE_ALIGNED(Name, Static, T, Alignment, ...) \
    typedef STRUCT_ALIGNED(Alignment) {                            \
        bool ok;                                                   \
        union {                                                    \
            T value;                                               \
            const char* const error;                               \
        } u;                                                       \
    }                                                              \
    INTERNAL__OUTPUT_TOKEN(Name);                                  \
    INTERNAL__SIGNATURE(Name, Static, __VA_ARGS__);

#define INTERNAL__DECLARE(Name, Static, T, ...) INTERNAL__DECLARE_ALIGNED(Name, Static, T, 16, __VA_ARGS__)
#define INTERNAL__DECLARE_NO_STATIC(Name, T, ...) INTERNAL__DECLARE(Name, , T, __VA_ARGS__)

// Define output constructor
#define INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Suffix, Param, Ok, Assignment)                   \
    static inline INTERNAL__OUTPUT_TOKEN(Name) INTERNAL__CONCATENATE_TOKENS(Name, Suffix)(Param) { \
        return (INTERNAL__OUTPUT_TOKEN(Name)){.ok = (Ok), .u.Assignment};                          \
    }

// Define function and output constructors
#define INTERNAL__DEFINE_CAN_VOID(Name, Static, Param, Value, ...)                            \
    INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Err, const char* const err, false, error = err) \
    INTERNAL__DEFINE_OUTPUT_CONSTRUCTOR(Name, Ok, Param, true, value = Value)                 \
    INTERNAL__SIGNATURE(Name, Static, __VA_ARGS__)

#define INTERNAL__DEFINE_NULL(Name, Static, ...) INTERNAL__DEFINE_CAN_VOID(Name, Static, void, nullptr, __VA_ARGS__)

#define INTERNAL__DEFINE_LVALUE(Name, Static, T, ...) \
    INTERNAL__DEFINE_CAN_VOID(Name, Static, const T* const ptr, *ptr, __VA_ARGS__)

#define INTERNAL__DEFINE_RVALUE(Name, Static, T, ...) \
    INTERNAL__DEFINE_CAN_VOID(Name, Static, const T val, val, __VA_ARGS__)

/* ------------------------------------------------- Public macros ------------------------------------------------- */

#define DECLARATION_ALIGNED(Name, T, Alignment, ...) INTERNAL__DECLARE_ALIGNED(Name, , T, Alignment, __VA_ARGS__)
#define DECLARATION(Name, T, ...) INTERNAL__DECLARE_NO_STATIC(Name, T, __VA_ARGS__)
#define DECLARATION_VOID(Name, ...) INTERNAL__DECLARE_NO_STATIC(Name, nullptr_t, __VA_ARGS__)
#define DECLARATION_NO_PARAMS_STATIC(Name, T) INTERNAL__DECLARE(Name, static, T, void)

#define DEFINITION_NULL(Name, ...) INTERNAL__DEFINE_NULL(Name, , __VA_ARGS__)
#define DEFINITION_LVALUE(Name, T, ...) INTERNAL__DEFINE_LVALUE(Name, , T, __VA_ARGS__)
#define DEFINITION_LVALUE_NO_PARAMS_STATIC(Name, T) INTERNAL__DEFINE_LVALUE(Name, static, T, void)
#define DEFINITION_RVALUE(Name, T, ...) INTERNAL__DEFINE_RVALUE(Name, , T, __VA_ARGS__)