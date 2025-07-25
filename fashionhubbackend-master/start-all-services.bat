@echo off
echo Starting all microservices in order...
echo ==========================================

echo.
echo [1/8] Starting Eureka Server (Discovery Service)...
echo Starting on port 8761
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\eureka-server"
start "Eureka Server" cmd /c "mvn spring-boot:run"
echo Waiting for Eureka Server to start...
timeout /t 30 /nobreak > nul

echo.
echo [2/8] Starting Product Management Service...
echo Starting on port 8081
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\product-management-service"
start "Product Management Service" cmd /c "mvn spring-boot:run"
echo Waiting for Product Management Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [3/8] Starting Inventory Service...
echo Starting on port 8082
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\inventoryService"
start "Inventory Service" cmd /c "mvn spring-boot:run"
echo Waiting for Inventory Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [4/8] Starting Catalog Service...
echo Starting on port 8083
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\catalog-service"
start "Catalog Service" cmd /c "mvn spring-boot:run"
echo Waiting for Catalog Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [5/8] Starting User Service...
echo Starting on port 8084
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\UserService"
start "User Service" cmd /c "mvn spring-boot:run"
echo Waiting for User Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [6/8] Starting Cart and Checkout Service...
echo Starting on port 8085
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\cartAndCheckoutServiece"
start "Cart and Checkout Service" cmd /c "mvn spring-boot:run"
echo Waiting for Cart and Checkout Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [7/8] Starting Order Service...
echo Starting on port 8086
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\orderservice"
start "Order Service" cmd /c "mvn spring-boot:run"
echo Waiting for Order Service to start...
timeout /t 20 /nobreak > nul

echo.
echo [8/8] Starting API Gateway...
echo Starting on port 8090
cd /d "C:\Users\hparichha\IdeaProjects\FRONTEND + BACKEND\BACKEND\api-gateway"
start "API Gateway" cmd /c "mvn spring-boot:run"
echo Waiting for API Gateway to start...
timeout /t 20 /nobreak > nul

echo.
echo ==========================================
echo All services startup initiated!
echo ==========================================
echo.
echo Service URLs:
echo - Eureka Server: http://localhost:8761
echo - Product Management: http://localhost:8081
echo - Inventory Service: http://localhost:8082
echo - Catalog Service: http://localhost:8083
echo - User Service: http://localhost:8084
echo - Cart and Checkout: http://localhost:8085
echo - Order Service: http://localhost:8086
echo - API Gateway: http://localhost:8090
echo.
echo Swagger UI URLs (after services are fully started):
echo - Product Management: http://localhost:8081/swagger-ui.html
echo - Inventory Service: http://localhost:8082/swagger-ui.html
echo - Catalog Service: http://localhost:8083/swagger-ui.html
echo - User Service: http://localhost:8084/swagger-ui.html
echo - Cart and Checkout: http://localhost:8085/swagger-ui.html
echo - Order Service: http://localhost:8086/swagger-ui.html
echo - API Gateway: http://localhost:8090/swagger-ui.html
echo.
echo Note: Please wait a few minutes for all services to fully start before testing.
echo Press any key to continue...
pause > nul
