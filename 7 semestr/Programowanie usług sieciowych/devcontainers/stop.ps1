$ErrorActionPreference = "Stop"

Push-Location devcontainers

try
{
    # read active profile
    $profileFile = ".current-profile"
    if (-not (Test-Path $profileFile))
    {
        Write-Error "No active devcontainer profile. Run start.ps1 first."
        exit 1
    }

    $Profile = (Get-Content $profileFile -Raw).Trim()
    $compose = "$Profile/compose.yaml"
    if (-not (Test-Path $compose))
    {
        Write-Error "Profile '$Profile' from $profileFile does not exist."
        exit 1
    }

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
    Write-Host "Running: docker compose -f $compose down -v --remove-orphans"
    docker compose -f $compose down -v --remove-orphans

    # 4. clear temporary files
    Remove-Item $profileFile -ErrorAction SilentlyContinue
    Remove-Item mutagen.yml -ErrorAction SilentlyContinue
}
finally
{
    Pop-Location
}
