@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.parking
if "%PID%" == "" (
    echo No parking service process found
    exit /b 1
)
echo Found parking service process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo parking service process(%PID%) was stopped
endlocal