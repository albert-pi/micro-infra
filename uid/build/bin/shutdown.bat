@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.uid
if "%PID%" == "" (
    echo No UID service process found
    exit /b 1
)
echo Found UID service process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo UID service process(%PID%) was stopped
endlocal