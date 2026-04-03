#!/bin/bash
echo "============================================"
echo "  Hall Booking Management System"
echo "  Compiling all Java source files..."
echo "============================================"

# Create output folder
mkdir -p out

# Compile everything
javac -d out -sourcepath src src/Main.java src/model/*.java src/util/*.java src/gui/*.java

# Check for errors
if [ $? -ne 0 ]; then
    echo ""
    echo "ERROR: Compilation failed. Check error messages above."
    exit 1
fi

echo ""
echo "Compilation successful!"
echo "============================================"
echo "  Launching application..."
echo "============================================"
echo ""

# Run from out folder (data folder will be created relative to where you run from)
cd out
java Main
