## Multi-stage Dockerfile for production build of accel-connect (Spring Boot, Maven, Java 17)

# -------- Build stage --------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace/app

# Cache dependencies first
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -B -q -e -DskipTests package


# -------- Runtime stage --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the fat jar from the build stage
COPY --from=build /workspace/app/target/accel-connect-0.0.1-SNAPSHOT.jar /app/app.jar

# Allow arbitrary UID on OpenShift: grant group 0 ownership and mirror perms to group
RUN chgrp -R 0 /app /tmp && \
    chmod -R g=u /app /tmp

# Expose the application port (configured in application.properties)
EXPOSE 8085

# Recommended JVM flags for containers; override JAVA_OPTS at runtime if needed
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom"

# Start the application
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]