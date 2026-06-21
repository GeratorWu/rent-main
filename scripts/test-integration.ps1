# Script de test d'intégration Docker Compose
$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $PSScriptRoot
Set-Location $root

function Test-Endpoint {
    param(
        [string]$Name,
        [scriptblock]$Action
    )
    try {
        & $Action
        Write-Host "[OK] $Name" -ForegroundColor Green
        return $true
    } catch {
        Write-Host "[FAIL] $Name - $_" -ForegroundColor Red
        return $false
    }
}

Write-Host "=== Demarrage Docker Compose ===" -ForegroundColor Cyan
docker compose down 2>$null
docker compose up --build -d

Write-Host "=== Attente des services (20s) ===" -ForegroundColor Cyan
Start-Sleep -Seconds 20

$passed = 0
$total = 0

$tests = @(
    @{ Name = "MyService GET /"; Action = { Invoke-RestMethod http://localhost:8080/ | Out-Null } },
    @{ Name = "CustomerService GET /"; Action = { Invoke-RestMethod http://localhost:8081/ | Out-Null } },
    @{ Name = "MyService POST /cars"; Action = {
        Invoke-RestMethod -Method Post -Uri http://localhost:8080/cars `
            -ContentType "application/json" `
            -Body '{"plateNumber":"ABC123","brand":"Toyota","price":15000.0}' | Out-Null
    }},
    @{ Name = "MyService GET /cars/ABC123"; Action = {
        $car = Invoke-RestMethod http://localhost:8080/cars/ABC123
        if ($car.plateNumber -ne "ABC123") { throw "Voiture incorrecte" }
    }},
    @{ Name = "CustomerService POST /customers"; Action = {
        Invoke-RestMethod -Method Post -Uri http://localhost:8081/customers `
            -ContentType "application/json" `
            -Body '{"id":"C001","name":"Alice","email":"alice@example.com"}' | Out-Null
    }},
    @{ Name = "CustomerService GET /customers/C001"; Action = {
        $customer = Invoke-RestMethod http://localhost:8081/customers/C001
        if ($customer.name -ne "Alice") { throw "Client incorrect" }
    }},
    @{ Name = "Frontend GET /"; Action = {
        $response = Invoke-WebRequest http://localhost:3000/ -UseBasicParsing
        if ($response.StatusCode -ne 200) { throw "Page inaccessible" }
    }},
    @{ Name = "Frontend proxy GET /api/cars"; Action = {
        Invoke-RestMethod http://localhost:3000/api/cars | Out-Null
    }},
    @{ Name = "Frontend proxy GET /api/customers"; Action = {
        Invoke-RestMethod http://localhost:3000/api/customers | Out-Null
    }}
)

foreach ($test in $tests) {
    $total++
    if (Test-Endpoint -Name $test.Name -Action $test.Action) {
        $passed++
    }
}

Write-Host "`n=== Resultat : $passed/$total tests reussis ===" -ForegroundColor Cyan

Write-Host "=== Arret Docker Compose ===" -ForegroundColor Cyan
docker compose down

if ($passed -ne $total) {
    exit 1
}
