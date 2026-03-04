#!/bin/bash

echo "Building Event-Driven Microservices Architecture..."
echo ""

# Build Order Service
echo "Building Order Service..."
cd order-service
mvn clean package -DskipTests
cd ..

# Build Inventory Service
echo "Building Inventory Service..."
cd inventory-service
mvn clean package -DskipTests
cd ..

# Build Billing Service
echo "Building Billing Service..."
cd billing-service
mvn clean package -DskipTests
cd ..

# Build API Gateway
echo "Building API Gateway..."
cd api-gateway
mvn clean package -DskipTests
cd ..

echo ""
echo "All services built successfully!"
echo ""
echo "Starting Docker Compose..."
docker-compose up --build

