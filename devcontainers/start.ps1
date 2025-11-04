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
        $cmd = "docker compose up --build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language"
        Write-Host $cmd -ForegroundColor Blue
        Invoke-Expression $cmd
    }
    else
    {
        if (-not $imageExists)
        {
            throw "Offline and image '$imageName' not present. Build it once while online first."
        }

        Write-Host "Offline: using existing image '$imageName' without rebuild." -ForegroundColor Red
        $cmd = "docker compose up --no-build --force-recreate --no-log-prefix --remove-orphans --quiet-pull -V --wait $Language"
        Write-Host $cmd -ForegroundColor Blue
        Invoke-Expression $cmd
    }

    # 3. start mutagen project
    (Get-Content mutagen.tpl.yml) -replace '{{LANGUAGE}}', $Language -replace '{{ALPHA}}', "../$Project" | Set-Content mutagen.yml
    $cmd = "$mutagenExe project start"
    Write-Host $cmd -ForegroundColor Blue
    Invoke-Expression $cmd
}
finally
{
    Pop-Location
}
