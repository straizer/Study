function Invoke
{
    param(
        [string]$Command
    )

    Write-Host $Command -ForegroundColor Blue
    Invoke-Expression "$Command 2>&1"
    if ($LASTEXITCODE -ne 0)
    {
        throw "``$Command`` failed with exit code $LASTEXITCODE"
    }
}
