@echo off
setlocal

:: Define path to the JavaFX SDK
set JAVAFX_SDK_PATH=..\lib\javafx-sdk-21.0.2\lib

:: Set the classpath
set CLASSPATH=.;..\lib\jnativehook-2.2.2.jar;

:: Compile the Java files
echo Compiling Java files...
javac -cp "%CLASSPATH%;%JAVAFX_SDK_PATH%\*" Main.java

:: If compilation yields error, don't run the application
if %ERRORLEVEL% neq 0 (
    echo Compilation failed, application will not run.
    goto end
)

:: Run the application
echo Running the application...
java --module-path "%JAVAFX_SDK_PATH%" --add-modules javafx.controls,javafx.fxml -cp "%CLASSPATH%" Main

:end
echo Finished
endlocal
