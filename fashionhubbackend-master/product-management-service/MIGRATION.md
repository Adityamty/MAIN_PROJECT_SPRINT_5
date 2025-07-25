# Migration Guide: From Microservices to Unified Service

## Overview
This guide explains how to migrate from the separate category-service and product-service microservices to the unified product-management-service.

## Current Architecture
- **category-service**: Running on port 8082
- **product-service**: Running on port 8085
- **Frontend**: Configured to use port 8000 (likely through a gateway)

## New Architecture
- **product-management-service**: Running on port 8085
- **Frontend**: Can be configured to use port 8085 directly or continue using port 8000

## Migration Steps

### Step 1: Test the Unified Service
1. Start the unified service:
   ```bash
   cd backend/product-management-service
   mvn spring-boot:run
   ```

2. Test the endpoints:
   - Categories: http://localhost:8085/categories
   - Products: http://localhost:8085/products

### Step 2: Update Frontend Configuration (Optional)
To use the unified service directly:

1. Update `frontend/.env`:
   ```properties
   VITE_API_BASE_URL=http://localhost:8085
   ```

2. Restart the frontend:
   ```bash
   cd frontend
   npm run dev
   ```

### Step 3: Gradual Migration
The unified service can coexist with the original microservices:

1. **Keep original services running** during testing
2. **Use port 8086** for the unified service if needed:
   ```yaml
   # In application.yml
   server:
     port: 8086
   ```
3. **Update frontend gradually** by changing the base URL
4. **Test all functionality** before decommissioning old services

### Step 4: Gateway Configuration
If you're using a gateway (port 8000), update routes:

```yaml
# Example gateway configuration
routes:
  - id: product-management
    uri: http://localhost:8085
    predicates:
      - Path=/products/**,/categories/**
```

### Step 5: Database Consistency
The unified service uses the same database and tables:
- ✅ No database migration needed
- ✅ Same table structure
- ✅ Same data relationships

## Benefits After Migration
- **Simplified Architecture**: One service instead of two
- **Better Performance**: No inter-service HTTP calls
- **Easier Maintenance**: Single codebase
- **Reduced Resource Usage**: One JVM instance

## Rollback Plan
If issues arise, you can easily rollback:
1. Stop the unified service
2. Restart the original microservices
3. Revert frontend configuration
4. No data loss as database remains unchanged

## Testing Checklist
- [ ] All category CRUD operations work
- [ ] All product CRUD operations work
- [ ] Product-category relationships work
- [ ] Search and filtering work
- [ ] Pagination works
- [ ] Database transactions work
- [ ] Error handling works
- [ ] CORS configuration works

## Port Configuration Summary
| Service | Original Port | Unified Port |
|---------|---------------|--------------|
| category-service | 8082 | 8085 |
| product-service | 8085 | 8085 |
| Frontend | 8000 | 8000 or 8085 |

## Notes
- The unified service maintains API compatibility
- All existing API endpoints continue to work
- Database schema remains unchanged
- Can run alongside original services during migration
