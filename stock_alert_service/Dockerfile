FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy everything to allow access to :shared
COPY . .

# Build only the alert service (and its dependencies)
RUN ./gradlew :stock_alert_service:bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/stock_alert_service/build/libs/*.jar app.jar

# Docker internal port
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]