# Stage 1: Build JAR using Maven and Eclipse Temurin JDK 21
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /workspace

# Cache Maven dependencies
COPY pom.xml .
COPY .mvn/ .mvn/
COPY mvnw* .
RUN if [ -f "./mvnw" ]; then ./mvnw dependency:go-offline -B; else apk add --no-cache maven && mvn dependency:go-offline -B; fi

# Copy source code and build production artifact
COPY src ./src
RUN if [ -f "./mvnw" ]; then ./mvnw clean package -DskipTests -B; else mvn clean package -DskipTests -B; fi

# Stage 2: Minimal Distroless/JRE runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Add a non-root system user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder --chown=appuser:appgroup /workspace/target/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]