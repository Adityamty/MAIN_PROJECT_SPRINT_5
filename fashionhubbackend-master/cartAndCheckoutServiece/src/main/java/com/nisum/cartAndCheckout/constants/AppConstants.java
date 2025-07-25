package com.nisum.cartAndCheckout.constants;

public class AppConstants {

    // Real Service URLs - Updated with actual microservice endpoints
    public static final String INVENTORY_AVAILABILITY_URL = "http://localhost:8082/api/inventory/skus-qty";
    public static final String INVENTORY_RESERVE_URL = "http://localhost:8082/api/inventory/reserve";
    public static final String INVENTORY_UPDATE_URL = "http://localhost:8082/api/inventory/adjust";
    public static final String INVENTORY_CANCEL_URL = "http://localhost:8082/api/inventory/adjust/cancel";
    public static final String INVENTORY_AVAILABLE_SKU_URL = "http://localhost:8082/api/inventory/available/{sku}";

    // Product Service URLs
    public static final String PRODUCT_SERVICE_URL = "http://localhost:8081/products";
    public static final String PRODUCT_BY_ID_URL = "http://localhost:8081/products/{id}";
    public static final String PRODUCT_ATTRIBUTES_URL = "http://localhost:8081/products/attributes";
    public static final String PRODUCT_SIZES_URL = "http://localhost:8081/products/{id}/sizes";
    public static final String PRODUCT_SKUS_URL = "http://localhost:8081/products/{id}/skus";
    public static final String PRODUCT_SELLER_URL = "http://localhost:8081/products/{id}/sellers";

    // User Service URLs
    public static final String USER_SERVICE_URL = "http://localhost:8084/user";
    public static final String USER_BASIC_INFO_URL = "http://localhost:8084/user/basic-info/{id}";
    public static final String USER_CURRENT_URL = "http://localhost:8084/user/current";

    // API Gateway URLs (for external access)
    public static final String GATEWAY_INVENTORY_URL = "http://localhost:8000/inventory-service/api/inventory/skus-qty";
    public static final String GATEWAY_PRODUCT_URL = "http://localhost:8000/product-management-service/products";
    public static final String GATEWAY_USER_URL = "http://localhost:8000/user-service/user";

    // TODO: Replace with real URLs when Order Management Service is implemented
    // Order Management Service URLs (Dummy URLs for now)
    public static final String OMS_ORDER_URL = "http://localhost:8086/api/orders/place";
    public static final String OMS_ORDER_STATUS_URL = "http://localhost:8086/api/orders/status";
    public static final String OMS_ORDER_HISTORY_URL = "http://localhost:8086/api/orders/history";

    // TODO: Replace with real URLs when Catalog Management Service is implemented
    // Catalog Management Service URLs (Dummy URLs for now)
    public static final String PROMO_SERVICE_URL = "http://localhost:8087/api/catalog/promos";
    public static final String CATALOG_SERVICE_URL = "http://localhost:8087/api/catalog";
    public static final String CATALOG_PRODUCTS_URL = "http://localhost:8087/api/catalog/products";

    // Session and Cart constants
    public static final String SESSION_USER_ID = "userId";

    // Error messages
    public static final String CART_NOT_FOUND = "Shopping cart not found for user: ";
    public static final String EMPTY_CART = "No items found in the cart for user: ";
    public static final String INVENTORY_UNAVAILABLE = "Inventory service is unavailable. Please try again later.";
    public static final String ORDER_FAILED = "Failed to create order";
    public static final String INVENTORY_UPDATE_FAILED = "Failed to update inventory after order placement.";
    public static final String SKU_NOT_FOUND = "SKU not found in inventory for: ";
    public static final String INSUFFICIENT_QUANTITY = "Insufficient quantity for SKU: ";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String AUTHENTICATION_FAILED = "Authentication failed";
    public static final String AUTHORIZATION_FAILED = "Authorization failed";

    // HTTP Status Messages
    public static final String SUCCESS_MESSAGE = "Operation completed successfully";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error occurred";
    public static final String BAD_REQUEST = "Bad request";
    public static final String UNAUTHORIZED = "Unauthorized access";
    public static final String FORBIDDEN = "Forbidden access";
    public static final String NOT_FOUND = "Resource not found";

    // Additional missing constants for UserValidator
    public static final String USER_NOT_LOGGED_IN = "User not logged in";
    public static final String USER_ADDRESS_NOT_FOUND = "User address not found";
}
