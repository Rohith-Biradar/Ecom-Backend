# 1. Start with the official Java 21 base image
# This creates a container with Java 21 installed.
FROM openjdk:21-slim

# 2. Set the working directory inside the container
# This is where your application files will live.
WORKDIR /app

# 3. Copy the compiled .jar file into the container
# This takes the JAR from your project's 'target' folder and renames it to 'app.jar' inside the container.
COPY target/ecomproject-0.0.1-SNAPSHOT.jar app.jar

# 4. Tell the container what command to run on startup
# This executes your Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]