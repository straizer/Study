$ErrorActionPreference = "Stop"

# assume ensure-mutagen.ps1 exists in same directory
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$ensureScript = Join-Path $scriptDir "ensure-mutagen.ps1"

if (-not (Test-Path $ensureScript))
{
    throw "ensure-mutagen.ps1 not found in $scriptDir"
}

# 1. ensure mutagen
$mutagenExe = & $ensureScript
if (-not $mutagenExe)
{
    throw "Failed to obtain mutagen path."
}

Write-Host "Using mutagen at: $mutagenExe"

# 2. stop mutagen project
Write-Host "Running: mutagen project terminate"
& $mutagenExe project terminate

# 3. stop docker compose
Write-Host "Running: docker compose down -v --remove-orphans"
docker compose down -v --remove-orphans
