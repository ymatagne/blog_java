@echo on

REM Windows script for running unit tests
REM You have to run server and capture some browser first
REM
REM Requirements:
REM - NodeJS (http://nodejs.org/)
REM - Testacular (npm install -g testacular)

set BASE_DIR=%~dp0
testacular.cmd start "%BASE_DIR%\..\resources\config\testacular.conf.js" %*