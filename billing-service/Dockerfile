FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY target/billing-service-1.0.0.jar /app/billing-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "billing-service.jar"]
