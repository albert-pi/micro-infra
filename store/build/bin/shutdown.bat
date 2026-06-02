@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.store
if "%PID%" == "" (
    echo No store service process found
    exit /b 1
)
echo Found store service process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo store service process(%PID%) was stopped
endlocal