param(
    [Parameter(Mandatory = $true)][ValidateSet('c')][string]$Language,
    [Parameter(Mandatory = $true)][string]$Project
)

$ErrorActionPreference = "Stop"

. "$PSScriptRoot\utils.ps1"

if (-not (Test-Path $Project))
{
    throw "Project '$Project' does not exist."
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
    $online = $false
    try
    {
        $test = Test-NetConnection -ComputerName "mcr.microsoft.com" -Port 443 -WarningAction SilentlyContinue -ErrorAction SilentlyContinue
        if ($test -and $test.TcpTestSucceeded)
        {
            $online = $true
        }
    }
    catch
    {
    }

    # check if image exists locally
    $imageName = "devcontainer-$Language"
    $imageExists = $false
    try
    {
        docker image inspect $imageName *> $null
        $imageExists = $true
    }
    catch
    {
    }

    if ($online)
    {
        Write-Host "Online: building and starting containers..." -ForegroundColor Green
        Invoke "docker compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language"
    }
    else
    {
        if (-not $imageExists)
        {
            throw "Offline and image '$imageName' not present. Build it once while online first."
        }

        Write-Host "Offline: using existing image '$imageName' without rebuild." -ForegroundColor Red
        Invoke "docker compose up --no-build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language"
    }

    # 3. start mutagen project
    (Get-Content mutagen.tpl.yml) -replace '{{LANGUAGE}}', $Language -replace '{{ALPHA}}', "../$Project" | Set-Content mutagen.yml
    Invoke "$mutagenExe project start"
}
finally
{
    Pop-Location
}
