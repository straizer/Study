param(
    [string]$InstallDir = "$env:LOCALAPPDATA\mutagen"
)

$ErrorActionPreference = "Stop"

$MutagenExeName = "mutagen.exe"

function Get-LatestMutagenInfo
{
    $api = "https://api.github.com/repos/mutagen-io/mutagen/releases/latest"
    $headers = @{ "User-Agent" = "mutagen-setup-script" }
    $release = Invoke-RestMethod -Uri $api -Headers $headers
    $asset = $release.assets | Where-Object {
        $_.name -like "mutagen_windows_amd64_*.zip"
    } | Select-Object -First 1

    if (-not $asset)
    {
        throw "No Windows amd64 asset found in latest Mutagen release."
    }

    [PSCustomObject]@{
        Tag = $release.tag_name
        Url = $asset.browser_download_url
        Name = $asset.name
    }
}

function Get-MutagenExePath
{
    param([string]$InstallDir, [string]$MutagenExeName)

    $paths = $env:PATH.Split([IO.Path]::PathSeparator)
    foreach ($p in $paths)
    {
        if ( [string]::IsNullOrWhiteSpace($p))
        {
            continue
        }
        $candidate = Join-Path $p $MutagenExeName
        if (Test-Path $candidate)
        {
            return $candidate
        }
    }

    $candidate = Join-Path $InstallDir $MutagenExeName
    if (Test-Path $candidate)
    {
        return $candidate
    }

    return $null
}

function Get-InstalledMutagenVersion
{
    param([string]$Exe)
    try
    {
        $out = & $Exe version 2> $null
        if ($out -match "(\d+\.\d+\.\d+)")
        {
            return $Matches[1]
        }
    }
    catch
    {
    }
    return $null
}

# main
$latest = Get-LatestMutagenInfo
$latestTag = $latest.Tag.TrimStart("v")   # e.g. "0.18.1"
$latestUrl = $latest.Url

$currentExe = Get-MutagenExePath -InstallDir $InstallDir -MutagenExeName $MutagenExeName

if ($currentExe)
{
    $installed = Get-InstalledMutagenVersion -Exe $currentExe
    if ($installed -and $installed -eq $latestTag)
    {
        Write-Host "mutagen $installed already installed at $currentExe"
        $currentExe
        return
    }
    else
    {
        Write-Host "mutagen version differs (installed: $installed, latest: $latestTag). Updating..."
    }
}
else
{
    Write-Host "mutagen not found. Installing..."
}

if (-not (Test-Path $InstallDir))
{
    New-Item -ItemType Directory -Path $InstallDir | Out-Null
}

$tmp = New-TemporaryFile
Remove-Item $tmp -Force
$tmpZip = "$tmp.zip"

Write-Host "Downloading $latestUrl ..."
Invoke-WebRequest -Uri $latestUrl -OutFile $tmpZip -UseBasicParsing

Write-Host "Extracting to $InstallDir ..."
Expand-Archive -Path $tmpZip -DestinationPath $InstallDir -Force

$exe = Join-Path $InstallDir $MutagenExeName
if (-not (Test-Path $exe))
{
    throw "mutagen.exe not found after extract."
}

if ($env:PATH.Split([IO.Path]::PathSeparator) -notcontains $InstallDir)
{
    $env:PATH = "$InstallDir" + [IO.Path]::PathSeparator + $env:PATH
}

Write-Host "mutagen $latestTag installed to $InstallDir"
$exe
