# Devcontainers

A lightweight, reproducible development environment for this repo powered by Docker, SSH, and Mutagen file sync. It
spins up language‑specific containers that you can attach to from your IDE (JetBrains Gateway/CLion Remote Dev or VS
Code Remote SSH), while syncing your local project files into the container.

## What’s inside

- Dockerfile with a base `ssh` image and language stages (currently: `cpp`).
- docker compose service exposing SSH on `127.0.0.1:2222` for the chosen language.
- PowerShell helpers: `start.ps1` and `stop.ps1`.
- Mutagen template for fast, selective file syncing.

## Prerequisites

- Windows with PowerShell
- Docker Desktop running
- Internet access (first build pulls base images and tools)
- Optional: JetBrains Gateway (for CLion remote dev) or VS Code with Remote SSH

## Quick start (C++)

1. From the repository root, run:
   ```powershell
   devcontainers\start.ps1 -Language cpp -Project "7 semestr\Programowanie usług sieciowych\PUS_LAB1\ex1"
   ```
   Notes:
    - `-Project` must point to the project folder relative to repo root.
    - The script will:
        - Ensure Mutagen is available (`ensure-mutagen.ps1`).
        - Build and start the docker compose service for `cpp`.
        - Launch a Mutagen project to sync your local project into `/home/dev/workspace` inside the container.

2. Connect your IDE:
    - JetBrains Gateway / CLion Remote Dev:
        - Host: `127.0.0.1`
        - Port: `2222`
        - User: `dev`
        - Password: (empty, press Enter)
        - Remote path: `/home/dev/workspace`
        - The image pre‑registers CLion Remote Dev backend.
    - VS Code (Remote SSH):
        - Add an SSH target `ssh dev@127.0.0.1 -p 2222` (empty password) and connect, then open `/home/dev/workspace`.

3. When you’re done:
   ```powershell
   .\devcontainers\stop.ps1
   ```
   This terminates the Mutagen project, stops containers, and cleans temp files.

## Folder structure

- `Dockerfile`
    - Stage `ssh`: Debian base with OpenSSH and a `dev` user.
    - Stage `cpp`: Adds CLion Remote Dev backend.
- `compose.yaml`
    - Defines the `cpp` service, exposes SSH `2222->22`.
- `start.ps1` / `stop.ps1`
    - Start/stop lifecycle and Mutagen project management.
- `mutagen.tpl.yml`
    - Two‑way‑resolved sync; ignores common build output and narrows `src` syncing.

## How to add another language

1. Create a new stage in `Dockerfile` based on `ssh`:
   ```dockerfile
   FROM ssh AS python
   # install toolchain, e.g. Python + pip + deps
   RUN apt-get install -y --no-install-recommends python3 python3-pip
   RUN rm -rf /var/lib/apt/lists/*
   # (Optionally register JetBrains remote backend for PyCharm if needed)
   ```
2. Add a compose service in `compose.yaml` (mirroring `cpp`):
   ```yaml
   services:
     python:
       image: devcontainer-python
       build:
         target: python
       container_name: python
       ports:
         - "127.0.0.1:2222:22"
   ```
3. Allow the new language in `start.ps1`:
    - Update the `ValidateSet('cpp')` to include your language, e.g. `ValidateSet('cpp','python')`.
4. Adjust sync rules if necessary in `mutagen.tpl.yml`:
    - Keep or tweak `ignore.paths` to match your language’s build artifacts.
    - The template is materialized into `mutagen.yml` automatically by `start.ps1`.
5. Start the new environment:
   ```powershell
   .\devcontainers\start.ps1 -Language python -Project "path\to\your\project"
   ```
   Then connect via SSH using the mapped port `2222`.

## Default connection details

- User: `dev`
- Password: empty
- Container workspace: `/home/dev/workspace`
- Healthcheck: SSH must be listening on port 22 inside the container.

## Tips

- If Docker images change, re‑run start with `--no-cache` by cleaning images, or run `docker compose build --no-cache`
  inside `devcontainers`.
- If file sync stalls, try:
  ```powershell
  devcontainers\stop.ps1
  devcontainers\start.ps1 -Language <name> -Project <path>
  ```
- Ports don't have to be unique per service since running multiple languages simultaneously is not supported.
