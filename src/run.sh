#!/bin/bash

# Define path to the JavaFX SDK
JAVAFX_SDK_PATH="../lib/javafx-sdk-21.0.2/lib"

# Set the classpath
CLASSPATH=".:../lib/jnativehook-2.2.2.jar"

# Compile the Java files
echo "Compiling Java files..."
javac -cp "${CLASSPATH}:${JAVAFX_SDK_PATH}/*" Main.java

# Check if compilation was successful
if [ $? -ne 0 ]; then
    echo "Compilation failed, application will not run."
    echo "Finished"
    exit 1
fi

# Run the application
echo "Running the application..."
java --module-path "${JAVAFX_SDK_PATH}" --add-modules javafx.controls,javafx.fxml -cp "${CLASSPATH}" Main

echo "Finished"
