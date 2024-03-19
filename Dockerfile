FROM openjdk:21-jdk-slim

# Install necessary OS libraries
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    libxkbcommon-x11-0 \
    libx11-xcb1 \
    libxt6 \
    libxinerama1 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the external library
COPY libs/jnativehook-2.2.2.jar /app/libs/

# Correct the source files path according to your project structure
# This copies your Java source files into the Docker image
COPY src/*.java /app/

# Compile your Java source files
# Adjust classpath to include the jnativehook library correctly
RUN javac -cp ".:libs/jnativehook-2.2.2.jar" *.java

# Adjust the classpath in the entrypoint to correctly include all necessary directories and libraries
# Ensure the Main class package name is correctly specified
ENTRYPOINT ["java", "-cp", ".:libs/*", "Main"]
