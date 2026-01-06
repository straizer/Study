$ErrorActionPreference = "Stop"

. "$PSScriptRoot\utils.ps1"

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
    Invoke "$mutagenExe project terminate"

    # 3. stop docker compose
    Invoke "docker compose down -v --remove-orphans"

    # 4. clear temporary file
    Remove-Item mutagen.yml -ErrorAction SilentlyContinue
}
finally
{
    Pop-Location
}
