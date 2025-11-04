param(
    [Parameter(Mandatory = $true)][ValidateSet('cpp')][string]$Language,
    [Parameter(Mandatory = $true)][string]$Project
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $Project))
{
    Write-Error "Project '$Project' does not exist."
    exit 1
}

Push-Location devcontainers

try
{
    # 1. ensure mutagen
    $mutagenExe = & ./ensure-mutagen.ps1
    if (-not $mutagenExe)
    {
        throw "Failed to obtain mutagen path."
    }

    # 2. run docker compose
    Write-Host "Running: compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language"
    docker compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language

    # 3. start mutagen project
    (Get-Content mutagen.tpl.yml) -replace '{{LANGUAGE}}', $Language -replace '{{ALPHA}}', "../$Project" | Set-Content mutagen.yml
    Write-Host "Running: mutagen project start"
    & $mutagenExe project start
}
finally
{
    Pop-Location
}
