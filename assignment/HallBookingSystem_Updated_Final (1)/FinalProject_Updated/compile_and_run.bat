@echo off
echo ============================================
echo   Hall Booking Management System
echo   Compiling all Java source files...
echo ============================================

REM Create output folder for compiled .class files
if not exist "out" mkdir out

REM Compile all Java files from src folder into out folder
javac -d out -sourcepath src src\Main.java src\model\*.java src\util\*.java src\gui\*.java

REM Check if compilation succeeded
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: Compilation failed. Please check the error messages above.
    pause
    exit /b 1
)

echo.
echo Compilation successful!
echo ============================================
echo   Launching application...
echo ============================================
echo.

REM Run the program from the out folder
REM The data folder must be next to the .java files in src, or we run from root
cd out
java Main

pause
