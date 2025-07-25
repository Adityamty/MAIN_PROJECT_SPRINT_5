# API Gateway for Microservices

This is a Spring Cloud Gateway project to route requests to UserService, CategoryService, and ProductService.

## How to Run All Services

1. Open four terminal windows (or tabs):
   - One for UserService
   - One for CategoryService
   - One for ProductService
   - One for api-gateway

2. In each terminal, navigate to the respective service folder:
   - `cd UserService`
   - `cd category-service/category-service`
   - `cd product-service/product-service`
   - `cd api-gateway`

3. Start each service using:
   - On Linux/Mac: `./mvnw spring-boot:run`
   - On Windows: `mvnw.cmd spring-boot:run`

4. The API Gateway will be available at http://localhost:8000

## Gateway Routing
- Requests to `/user/**` will be routed to UserService
- Requests to `/category/**` will be routed to CategoryService
- Requests to `/product/**` will be routed to ProductService


