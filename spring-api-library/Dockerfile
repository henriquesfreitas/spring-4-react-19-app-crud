# Stage 1: Build the application using a Gradle image with a full JDK
# This stage compiles the code and produces the executable JAR file.
FROM gradle:8.14-jdk-jammy AS build

WORKDIR /home/gradle/src

# Copy the entire project context into the build container
COPY --chown=gradle:gradle . .

# Execute the Gradle build command to compile and package the application.
# --no-daemon is used for better CI/CD compatibility.
RUN gradle build --no-daemon

# Stage 2: Create the final, lightweight production image
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]