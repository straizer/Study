$ErrorActionPreference = "Stop"

Push-Location devcontainers

try
{
    # 1. ensure mutagen
    $mutagenExe = & ./ensure-mutagen.ps1
    if (-not $mutagenExe)
    {
        throw "Failed to obtain mutagen path."
    }

    # 2. stop mutagen project
    Write-Host "Running: mutagen project terminate"
    & $mutagenExe project terminate

    # 3. stop docker compose
    Write-Host "Running: docker compose down -v --remove-orphans"
    docker compose down -v --remove-orphans

    # 4. clear temporary file
    Remove-Item mutagen.yml -ErrorAction SilentlyContinue
}
finally
{
    Pop-Location
}
