#!/bin/sh
set -eu

# Start SSH server
/usr/sbin/sshd -D &

WORKSPACE=/home/dev/workspace
COMMON=/home/dev/common

# Wait until Mutagen has mounted workspace
i=0
while [ -z "$(ls -A "$WORKSPACE" 2>/dev/null || true)" ]; do
  i=$((i+1))
  echo "Waiting for $WORKSPACE to become non-empty for $i seconds"
  if [ "$i" -gt 300 ]; then
    echo "Timeout waiting for $WORKSPACE to become non-empty" >&2
    ls -la "$WORKSPACE" || true
    exit 1
  fi
  sleep 1
done

sync_symlinks() {
  # Create symlinks in WORKSPACE for entries in COMMON only when missing in WORKSPACE.
  # Also remove stale symlinks that point into COMMON whose targets no longer exist.
  cd "$COMMON"

 # Ensure directories exist in workspace (real dirs if missing)
  find . -type d -print | while read -r d; do
    [ "$d" = "." ] && continue
    if [ -L "$WORKSPACE/$d" ]; then
      continue
    fi
    mkdir -p "$WORKSPACE/$d"
  done

  # Create symlinks for files missing in workspace
  find . -type f -print | while read -r f; do
    if [ -e "$WORKSPACE/$f" ] || [ -L "$WORKSPACE/$f" ]; then
      continue
    fi
    mkdir -p "$(dirname "$WORKSPACE/$f")"
    ln -s "$COMMON/$f" "$WORKSPACE/$f"
  done

  # Cleanup stale links that point into COMMON
  find "$WORKSPACE" -type l -print | while read -r l; do
    target="$(readlink "$l" || true)"
    case "$target" in
      "$COMMON"/*)
        [ -e "$target" ] || rm -f "$l"
        ;;
    esac
  done
}

# First sync
sync_symlinks

# Watch common for changes and refresh symlinks.
(
  while inotifywait -r -e create,delete,modify,move "$COMMON" >/dev/null 2>&1; do
    sync_symlinks
  done
) &

# Run the container command (keep FUSE in background)
exec "$@"
