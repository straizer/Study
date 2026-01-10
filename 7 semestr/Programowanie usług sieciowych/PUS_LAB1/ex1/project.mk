### This file should declare ALL_BINS, ALL_OBJS, help target, and define linking.
### Optionally, there may be added additional targets for running binaries.

# Define executables and their object files
COMMON_OBJS = $(OBJ_DIR)/string.o $(OBJ_DIR)/errors.o $(OBJ_DIR)/utils.o $(OBJ_DIR)/network.o $(OBJ_DIR)/ipv4.o \
 $(OBJ_DIR)/io.o
CLIENT_OBJS = $(OBJ_DIR)/client.o $(COMMON_OBJS)
SERVER_OBJS = $(OBJ_DIR)/server.o $(COMMON_OBJS)

CLIENT_BIN = $(BIN_DIR)/client
SERVER_BIN = $(BIN_DIR)/server

# All targets
ALL_BINS = $(CLIENT_BIN) $(SERVER_BIN)
ALL_OBJS = $(CLIENT_OBJS) $(SERVER_OBJS)

# Default target
help:
	echo "No default target. Use 'make release' or 'make debug' to build." >&2
	echo "Available targets:" >&2
	echo "  make release                        - Build all programs in release mode" >&2
	echo "  make debug                          - Build all programs in debug mode" >&2
	echo "  make run-client IP=<ip> PORT=<port> - Run client" >&2
	echo "  make run-server PORT=<port>         - Run server" >&2
	echo "  make clean                          - Remove compiled files" >&2
	exit 0

# Link client executable
$(CLIENT_BIN): $(CLIENT_OBJS)
	echo "Linking $@."
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o $@

# Link server executable
$(SERVER_BIN): $(SERVER_OBJS)
	echo "Linking $@."
	$(CC) $(LDFLAGS) $^ $(LDLIBS) -o $@

#PHONY targets
.PHONY: run-client run-server

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