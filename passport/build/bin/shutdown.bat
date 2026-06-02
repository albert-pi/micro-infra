@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID microinfra.passport
if "%PID%" == "" (
    echo No passport service process found
    exit /b 1
)
echo Found passport service process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo passport service process(%PID%) was stopped
endlocal