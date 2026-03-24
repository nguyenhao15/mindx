# Stage 1: build with Maven + JDK 17
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy maven files first to leverage layer caching
COPY pom.xml .
# If you use <modules> or other files, add them similarly
COPY src ./src

# Optional: use local m2 cache to speed builds when building locally:
# docker build --build-arg MAVEN_CONFIG=/root/.m2 -t demo01:latest .
RUN mvn -B -e -DskipTests package

# Stage 2: runtime
FROM eclipse-temurin:17-jre-jammy
ARG JAR_FILE=target/demo01.jar
WORKDIR /app

# Copy the jar built in the builder stage
COPY --from=builder /workspace/${JAR_FILE} ./app.jar

# Create non-root user (optional but recommended)
RUN useradd -m appuser && chown -R appuser:appuser /app
USER appuser

EXPOSE 8080
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]