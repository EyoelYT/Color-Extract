#!/bin/bash

# Setting the classpath
# Add JavaFX libs to the classpath when needed
CLASSPATH=".:./jnativehook-2.2.2.jar"

# Compile Java files
echo "Compiling Java files..."
javac -cp "$CLASSPATH" Main.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# Run the application
echo "Running the application..."
java -cp "$CLASSPATH" Main

echo "Finished"
