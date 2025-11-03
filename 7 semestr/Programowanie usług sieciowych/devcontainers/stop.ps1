param(
    [Parameter(Mandatory = $true)][ValidateSet('cpp')][string]$Profile
)

$ErrorActionPreference = "Stop"

$compose = "devcontainers/$Profile/compose.yaml"
if (-not (Test-Path $compose))
{
    Write-Error "Profile '$Profile' does not exist."
    exit 1
}

# 1. ensure mutagen
$mutagenExe = & "devcontainers/ensure-mutagen.ps1"
if (-not $mutagenExe)
{
    throw "Failed to obtain mutagen path."
}

# 2. stop mutagen project
Write-Host "Running: mutagen project terminate"
& $mutagenExe project terminate

# 3. stop docker compose
Write-Host "Running: docker compose -f $compose down -v --remove-orphans"
docker compose -f $compose down -v --remove-orphans
