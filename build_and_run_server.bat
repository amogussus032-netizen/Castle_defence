@echo off
chcp 65001 >nul
setlocal

set "PROJECT_DIR=C:\Users\amogu\Desktop\Java plugins\Castle_defence"
set "SERVER_DIR=C:\Users\amogu\Desktop\Castle_defence_server"
set "MVN=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.2\plugins\maven\lib\maven3\bin\mvn.cmd"
set "JAVA_HOME=C:\Users\amogu\AppData\Local\Programs\Eclipse Adoptium\jdk-25.0.2.10-hotspot"

echo === Stopping any running test server ===
powershell -NoProfile -Command "Get-CimInstance Win32_Process -Filter \"Name='java.exe'\" | Where-Object { $_.CommandLine -like '*server.jar*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force; Write-Output ('Stopped PID ' + $_.ProcessId) }"

echo === Building plugin ===
cd /d "%PROJECT_DIR%"
call "%MVN%" -q clean package
if errorlevel 1 (
    echo BUILD FAILED
    exit /b 1
)

echo === Deploying jar ===
copy /Y "target\castle_defence-0.1.jar" "%SERVER_DIR%\plugins\castle_defence-0.1.jar"

echo === Starting server ===
cd /d "%SERVER_DIR%"
java -Xms4G -Xmx4G -jar server.jar nogui

endlocal
