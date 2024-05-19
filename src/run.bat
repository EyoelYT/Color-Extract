@echo off
setlocal

:: Set the classpath
:: ;.\javafx-sdk-21.0.2\lib\ (add this later)
set CLASSPATH=.;.\jnativehook-2.2.2.jar;

:: Compile the Java files
echo Compiling Java files...
javac -cp "%CLASSPATH%" Main.java

:: Run the application
echo Running the application...
java -cp "%CLASSPATH%" Main

echo Finished
endlocal
