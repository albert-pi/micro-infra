@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.app
if "%PID%" == "" (
    echo No application process found
    exit /b 1
)
echo Found application process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo application process(%PID%) was stopped
endlocal