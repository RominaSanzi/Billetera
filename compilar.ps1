# Compila el TP (sin BilleteraTest, que necesita JUnit)
$jdk = "C:\Program Files\Microsoft\jdk-17.0.19.10-hotspot\bin"
$javac = Join-Path $jdk "javac.exe"
$java = Join-Path $jdk "java.exe"

if (-not (Test-Path $javac)) {
    Write-Host "No encuentro el JDK en: $jdk"
    Write-Host "Cambia la ruta en compilar.ps1 si instalaste otro JDK."
    exit 1
}

New-Item -ItemType Directory -Force -Path out | Out-Null
$archivos = Get-ChildItem "src\Billetera\*.java" | Where-Object { $_.Name -ne "BilleteraTest.java" }

& $javac -encoding UTF-8 -d out $archivos.FullName
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "Compilado OK. Para correr: .\compilar.ps1 -ejecutar"
if ($args -contains "-ejecutar") {
    & $java -cp out Billetera.Principal
}
