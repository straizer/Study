param(
    [Parameter(Mandatory = $true)][ValidateSet('cpp')][string]$Profile,
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
    $compose = "$Profile/compose.yaml"
    if (-not (Test-Path $compose))
    {
        Write-Error "Profile '$Profile' does not exist."
        exit 1
    }

    # remember which profile is active
    Set-Content ".current-profile" $Profile

    # 1. ensure mutagen
    $mutagenExe = & ./ensure-mutagen.ps1
    if (-not $mutagenExe)
    {
        throw "Failed to obtain mutagen path."
    }

    # 2. run docker compose
    Write-Host "Running: compose -f $compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait"
    docker compose -f $compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait

    # 3. start mutagen project
    (Get-Content mutagen.tpl.yml) -replace '{{ALPHA}}', "../$Project" | Set-Content mutagen.yml
    Write-Host "Running: mutagen project start (alpha=$Project)"
    & $mutagenExe project start
}
finally
{
    Pop-Location
}
