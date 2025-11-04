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
    $cmd = "$mutagenExe project terminate"
    Write-Host $cmd -ForegroundColor Blue
    Invoke-Expression $cmd

    # 3. stop docker compose
    $cmd = "docker compose down -v --remove-orphans"
    Write-Host $cmd -ForegroundColor Blue
    Invoke-Expression $cmd

    # 4. clear temporary file
    Remove-Item mutagen.yml -ErrorAction SilentlyContinue
}
finally
{
    Pop-Location
}
