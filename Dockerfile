# --- Build Stage ---
# Use a Maven image to build the project
FROM maven:3.9-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file first to leverage Docker layer caching
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Run the Maven package command to build the .jar file (skipping tests)
RUN mvn clean package -DskipTests


# --- Final Stage ---
# Use a slim Java 21 image to run the application
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the .jar file created from the 'builder' stage
COPY --from=builder /app/target/ecomproject-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
