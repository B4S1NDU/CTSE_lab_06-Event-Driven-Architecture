FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/order-service-1.0.0.jar /app/order-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "order-service.jar"]
