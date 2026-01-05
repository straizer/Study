# Special targets
.DEFAULT_GOAL := help
.DELETE_ON_ERROR:
.SILENT:

# Disable directory print messages
MAKEFLAGS += --no-print-directory

# Compiler
CC = gcc
CSTD = c23

# Preprocessor flags
CPPFLAGS_COMMON = -MMD -MP -D_FORTIFY_SOURCE=2 -D_GNU_SOURCE -D_FILE_OFFSET_BITS=64 -I$(SRC_DIR) #-I$(INC_DIR)
CPPFLAGS_DEBUG = $(CPPFLAGS_COMMON) -DDEBUG
CPPFLAGS_RELEASE = $(CPPFLAGS_COMMON) -DNDEBUG

# Compiler flags
CFLAGS_COMMON = -std=$(CSTD) -pthread \
  -Wall -Wextra -Werror -pedantic -pedantic-errors -pipe \
  -Wformat=2 -Wformat-security -Wformat-overflow=2 -Wformat-truncation=2 -Wformat-signedness \
  -Wstrict-prototypes -Wmissing-prototypes -Wmissing-declarations \
  -Wshadow -Wconversion -Wsign-conversion \
  -Wcast-qual -Wcast-align -Walloca -Wwrite-strings \
  -Wnull-dereference -Wdouble-promotion -Wfloat-equal -Wlogical-op \
  -Wpointer-arith -Wbad-function-cast -Wtrampolines \
  -Warray-bounds=2 -Wimplicit-fallthrough=5 -Wvla \
  -Wundef -Wredundant-decls -Wjump-misses-init -Wstrict-overflow=5 \
  -Wswitch-enum -Wduplicated-cond -Wduplicated-branches -Wrestrict \
  -Wstringop-truncation -Wstringop-overflow=3 \
  -Wstrict-aliasing=3 -Wmissing-include-dirs -Woverlength-strings \
  -Wdate-time -Wcpp -Wold-style-definition -Wendif-labels \
  -fno-common -fdiagnostics-color=always -fstack-protector-strong
CFLAGS_DEBUG = $(CFLAGS_COMMON) -O0 -g3 -ggdb3 -fno-pie \
  -fno-omit-frame-pointer -fno-optimize-sibling-calls \
  -fvar-tracking-assignments -fno-inline -fno-inline-functions-called-once \
  -fsanitize=address,undefined,leak -fno-sanitize-recover=all -fanalyzer
CFLAGS_RELEASE = $(CFLAGS_COMMON) -O3 -march=native -mtune=native -fPIE \
  -flto -ffunction-sections -fdata-sections -fvisibility=hidden \
  -fomit-frame-pointer -fno-plt -fno-semantic-interposition

# Linker flags
LDFLAGS_COMMON = -fuse-ld=gold -pthread -Wl,--fatal-warnings -Wl,--warn-common -Wl,--no-undefined \
  -Wl,-z,defs -Wl,-z,relro -Wl,-z,now -Wl,-z,noexecstack -Wl,--enable-new-dtags \
  -Wl,--as-needed -Wl,--build-id -Wl,--hash-style=gnu -Wl,--warn-shared-textrel
LDFLAGS_DEBUG = $(LDFLAGS_COMMON) -no-pie -Wl,-O0 -Wl,--export-dynamic -Wl,--gdb-index -Wl,-Map,$@.map \
  -fsanitize=address,undefined,leak -fno-sanitize-recover=all
LDFLAGS_RELEASE = $(LDFLAGS_COMMON) -pie -flto -Wl,--gc-sections -Wl,-O1 -Wl,--icf=all

# Cppcheck flags
CPPCHECK_FLAGS = -f -q -j 32 --platform=unix64 --language=c --std=$(CSTD) --template=gcc --inline-suppr \
  --check-level=exhaustive --max-ctu-depth=10 --report-type=misra-c-2023 --suppressions-list=.cppcheck-suppress \
  --inconclusive --library=/usr/share/cppcheck/cfg/posix.cfg,posix.cfg.xml --check-library --error-exitcode=1 \
  #--addon=misra.json

# Linked libraries
LDLIBS =

# Directories
SRC_DIR = src
INC_DIR = include
BIN_DIR = bin
OBJ_DIR = obj

# Define executables and their object files
NETWORK_OBJS = $(OBJ_DIR)/network.o $(OBJ_DIR)/ipv4.o
CLIENT_OBJS = $(OBJ_DIR)/client.o $(NETWORK_OBJS)
SERVER_OBJS = $(OBJ_DIR)/server.o $(NETWORK_OBJS)

CLIENT_BIN = $(BIN_DIR)/client
SERVER_BIN = $(BIN_DIR)/server

# All targets
ALL_BINS = $(CLIENT_BIN) $(SERVER_BIN)
ALL_OBJS = $(CLIENT_OBJS) $(SERVER_OBJS)

# Build mode tracking
MODE_FILE = $(OBJ_DIR)/build_mode.txt

# Include dependency files if they exist
-include $(ALL_OBJS:.o=.d)

# Default target
.PHONY: help
help:
	echo "No default target. Use 'make release' or 'make debug' to build." >&2
	echo "Available targets:" >&2
	echo "  make release                        - Build all programs in release mode" >&2
	echo "  make debug                          - Build all programs in debug mode" >&2
	echo "  make run-client IP=<ip> PORT=<port> - Run client" >&2
	echo "  make run-server PORT=<port>         - Run server" >&2
	echo "  make clean                          - Remove compiled files" >&2
	exit 0

LINT ?= 1

# Pattern rule: create directory
%/:
	echo "\033[36mCreating $@ directory.\033[0m"
	mkdir -p $@

# Pattern rule: compile .c to .o
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.c | $(OBJ_DIR)/ $(OBJ_DIR)/analysis/
ifneq ($(LINT),0) # Without it, Clion used `clang-format` or `clang-tidy` as a compiler.
	echo "Processing $<:"
	echo "\tFormatting..."
	clang-format -i $<
	echo "\tLinting..."
	clang-tidy --quiet $< -- -x c -std=$(CSTD) $(filter -I% -D%,$(CPPFLAGS) $(CFLAGS)) 2>/dev/null
	echo "\tStatic analysis..."
	cppcheck $(CPPCHECK_FLAGS) --cppcheck-build-dir=$(OBJ_DIR)/analysis --enable=all --suppress=unusedFunction $<
endif
	echo "\tCompiling..."
	$(CC) $(CPPFLAGS) $(CFLAGS) -c $< -o $@

check-unused-functions: $(OBJ_DIR)/unused/
	echo "\033[33mLooking for unused functions.\033[0m"
	cppcheck $(CPPCHECK_FLAGS) --cppcheck-build-dir=$(OBJ_DIR)/unused --enable=unusedFunction $(SRC_DIR)

# Link client executable
$(CLIENT_BIN): $(CLIENT_OBJS) | check-unused-functions $(BIN_DIR)/
	echo "Linking $@."
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o $@

# Link server executable
$(SERVER_BIN): $(SERVER_OBJS) | check-unused-functions $(BIN_DIR)/
	echo "Linking $@."
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o $@

# Build all in release mode
release: CPPFLAGS = $(CPPFLAGS_RELEASE)
release: CFLAGS = $(CFLAGS_RELEASE)
release: LDFLAGS = $(LDFLAGS_RELEASE)
release: check-mode-release $(ALL_BINS)
	echo "RELEASE" > $(MODE_FILE)
	echo "\033[32mRelease build complete.\033[0m"

# Build all in debug mode
debug: CPPFLAGS = $(CPPFLAGS_DEBUG)
debug: CFLAGS = $(CFLAGS_DEBUG)
debug: LDFLAGS = $(LDFLAGS_DEBUG)
debug: check-mode-debug $(ALL_BINS)
	echo "DEBUG" > $(MODE_FILE)
	echo "\033[32mDebug build complete.\033[0m"

# Check if we need to clean due to mode change
.PHONY: check-mode-release check-mode-debug
check-mode-release:
	if [ -f $(MODE_FILE) ] && [ "$$(cat $(MODE_FILE))" != "RELEASE" ]; then \
		echo "Build mode changed, cleaning."; \
		$(MAKE) clean; \
	fi
check-mode-debug:
	if [ -f $(MODE_FILE) ] && [ "$$(cat $(MODE_FILE))" != "DEBUG" ]; then \
		echo "Build mode changed, cleaning."; \
		$(MAKE) clean; \
	fi

# Run client
run-client:
	if [ ! -f $(CLIENT_BIN) ]; then \
		echo "Error: client executable not found. Run 'make release' or 'make debug' first." >&2; \
		exit 1; \
	fi
	echo "Running client."
	$(CLIENT_BIN) $(IP) $(PORT)

# Run server
run-server:
	if [ ! -f $(SERVER_BIN) ]; then \
		echo "Error: server executable not found. Run 'make release' or 'make debug' first." >&2; \
		exit 1; \
	fi
	echo "Running server."
	$(SERVER_BIN) $(PORT)

# Clean compiled files
clean:
	echo "Cleaning build artifacts."
	$(RM) -r -- $(OBJ_DIR) $(BIN_DIR)
	echo "\033[32mClean complete.\033[0m"

# Phony targets
.PHONY: release debug run-client run-server clean
