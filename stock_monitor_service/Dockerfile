FROM gradle:8.5-jdk21 AS build
WORKDIR /app

COPY . .

# Build only the monitor service
RUN ./gradlew :stock_monitor_service:bootJar --no-daemon

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/stock_monitor_service/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]