# Product Management Service

## Overview
This is a unified Spring Boot service that combines both product and category management functionalities. It replaces the separate product-service and category-service microservices with a single, consolidated service.

## Features
- **Product Management**: Create, read, update, and delete products
- **Category Management**: Create, read, update, and delete categories
- **Product Attributes**: Manage product attributes with SKU, pricing, and sizing
- **Seller Management**: Handle seller information
- **Pagination & Filtering**: Advanced product search and filtering capabilities
- **Database Integration**: Uses SQL Server with JPA/Hibernate

## Technology Stack
- **Framework**: Spring Boot 3.5.3
- **Java Version**: 17
- **Database**: Microsoft SQL Server
- **Build Tool**: Maven
- **ORM**: JPA with Hibernate

## Configuration
The service runs on port **8001** and connects to the same database as the original services:
- Database: `pim_db`
- Port: 8001
- Connection Pool: HikariCP with optimized settings

## API Endpoints

### Category Management
- `GET /categories` - Get all categories
- `GET /categories/{id}` - Get category by ID
- `POST /categories` - Create new category
- `PUT /categories/{id}` - Update category
- `DELETE /categories/{id}` - Delete category

### Product Management
- `GET /products` - Get all products with pagination and filtering
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create new product
- `PUT /products/{id}` - Update product
- `DELETE /products/{id}` - Delete product
- `GET /products/stats` - Get product statistics

### Product Filtering Parameters
- `page` - Page number (default: 1)
- `size` - Page size (default: 10)
- `status` - Filter by product status
- `search` - Search in product names
- `categoryId` - Filter by category
- `minPrice` & `maxPrice` - Price range filtering
- `sortBy` - Sort field (default: lastModifiedDate)
- `sort` - Sort direction (asc/desc, default: desc)

## Running the Service

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- SQL Server with `pim_db` database

### Build and Run
```bash
# Build the project
mvn clean compile

# Run the service
mvn spring-boot:run
```

The service will start on http://localhost:8001

## Database Schema
The service uses the same database schema as the original microservices:
- `categories` table for category data
- `products` table for product data
- `product_attributes` table for product attributes
- `seller` table for seller information

## Migration from Microservices
This unified service is designed to be a drop-in replacement for the separate microservices:

1. **Port**: Uses port 8001 (different from original services for coexistence)
2. **Database**: Uses the same database and tables
3. **API Compatibility**: Maintains the same API endpoints and response formats
4. **Configuration**: Uses optimized connection pooling and JPA settings

## Benefits of Unified Service
- **Reduced Complexity**: Single service to deploy and maintain
- **Better Performance**: Direct database access without inter-service calls
- **Simplified Deployment**: One application instead of two
- **Reduced Resource Usage**: Single JVM instance and connection pool
- **Easier Testing**: All functionality in one place

## Coexistence with Original Services
The unified service can run alongside the original microservices during migration:
- Use a different port if needed
- Gradually migrate frontend calls to the new service
- Keep original services as backup during transition period
