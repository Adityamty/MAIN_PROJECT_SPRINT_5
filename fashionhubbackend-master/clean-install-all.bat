@echo off
echo Starting complete clean install for all microservices...
echo.

echo [1/8] Cleaning and installing Eureka Server...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\eureka-server"
call mvn clean install -DskipTests

echo Eureka Server: COMPLETED
echo.

echo [2/8] Cleaning and installing API Gateway...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\api-gateway"
call mvn clean install -DskipTests
echo API Gateway: COMPLETED
echo.

echo [3/8] Cleaning and installing User Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\UserService"
call mvn clean install -DskipTests
echo User Service: COMPLETED
echo.

echo [4/8] Cleaning and installing Product Management Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\product-management-service"
call mvn clean install -DskipTests
echo Product Management Service: COMPLETED
echo.

echo [5/8] Cleaning and installing Inventory Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\inventoryService"
call mvn clean install -DskipTests
echo Inventory Service: COMPLETED
echo.

echo [6/8] Cleaning and installing Cart and Checkout Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\cartAndCheckoutServiece"
call mvn clean install -DskipTests
echo Cart and Checkout Service: COMPLETED
echo.

echo [7/8] Cleaning and installing Catalog Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\catalog-service"
call mvn clean install -DskipTests
echo Catalog Service: COMPLETED
echo.

echo [8/8] Cleaning and installing Order Service...
cd "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\orderservice"
call mvn clean install -DskipTests
echo Order Service: COMPLETED
echo.

echo ============================================
echo All microservices clean install completed!
echo ============================================
echo.
echo Service Port Configuration:
echo - Eureka Server: 8761
echo - API Gateway: 8000
echo - User Service: 8084
echo - Product Management Service: 8082
echo - Inventory Service: 8085
echo - Cart and Checkout Service: 8081
echo - Catalog Service: 8083
echo - Order Service: 8086
echo ============================================
pause
