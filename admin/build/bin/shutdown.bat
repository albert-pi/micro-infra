@echo off
setlocal enabledelayedexpansion

set PID=
call "%~dp0pid.bat" PID zhongzhi.admin
if "%PID%" == "" (
    echo No system administration process found
    exit /b 1
)
echo Found system administration process(%PID%) and stopping it
wmic process where "processid=%PID%" delete
echo system administration process(%PID%) was stopped
endlocal