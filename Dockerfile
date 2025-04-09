# Use official OpenJDK 17 image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy your jar file into the container
COPY matrix-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (e.g., 8881)
EXPOSE 8881

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]