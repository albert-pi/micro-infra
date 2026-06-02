@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.gateway
if "%PID%" == "" (
    echo No gateway process found
    exit /b 1
)
echo Found gateway process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo gateway process(%PID%) was stopped
endlocal