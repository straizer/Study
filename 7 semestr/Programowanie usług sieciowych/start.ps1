param(
    [Parameter(Mandatory = $true)]
    [string]$Project
)

$ErrorActionPreference = "Stop"

if (-not (Test-Path $Project))
{
    Write-Error "Directory '$Project' does not exist."
    exit 1
}

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

# clean previous session
& $mutagenExe project terminate *> $null | Out-Null

# 2. run docker compose
Write-Host "Running: compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait"
docker compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait

# 3. start mutagen project
(Get-Content mutagen.tpl.yml) -replace '{{ALPHA}}', $Project | Set-Content mutagen.yml
Write-Host "Running: mutagen project start (alpha=$Project)"
& $mutagenExe project start
