@echo off
setlocal enabledelayedexpansion

echo Building Event-Driven Microservices Architecture...
echo.

REM Build Order Service
echo Building Order Service...
cd order-service
call mvn clean package -DskipTests
cd ..

REM Build Inventory Service
echo Building Inventory Service...
cd inventory-service
call mvn clean package -DskipTests
cd ..

REM Build Billing Service
echo Building Billing Service...
cd billing-service
call mvn clean package -DskipTests
cd ..

REM Build API Gateway
echo Building API Gateway...
cd api-gateway
call mvn clean package -DskipTests
cd ..

echo.
echo All services built successfully!
echo.
echo Starting Docker Compose...
docker-compose up --build

endlocal
