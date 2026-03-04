FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/inventory-service-1.0.0.jar /app/inventory-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "inventory-service.jar"]
