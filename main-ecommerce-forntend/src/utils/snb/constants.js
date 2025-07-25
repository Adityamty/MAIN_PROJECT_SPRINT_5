// API Configuration
export const API_CONFIG = {
  BASE_URL: 'http://localhost:8080/api',
  TIMEOUT: 10000,
  RETRY_ATTEMPTS: 3,
};

// Image Configuration  
export const IMAGE_CONFIG = {
  PLACEHOLDER: '/api/placeholder/400/400',
  LAZY_LOADING: true,
  FALLBACK_IMAGES: {
    product: '/images/product-placeholder.png',
    category: '/images/category-placeholder.png',
  },
};

// UI Constants
export const UI_CONFIG = {
  ITEMS_PER_PAGE: 12,
  DEBOUNCE_DELAY: 300,
  ANIMATION_DURATION: 300,
  TOAST_DURATION: 3000,
};

// Product Configuration
export const PRODUCT_CONFIG = {
  SIZES: ['XS', 'S', 'M', 'L', 'XL', 'XXL'],
  SORT_OPTIONS: [
    { value: 'name', label: 'Name A-Z' },
    { value: 'name-desc', label: 'Name Z-A' },
    { value: 'price', label: 'Price: Low to High' },
    { value: 'price-desc', label: 'Price: High to Low' },
    { value: 'newest', label: 'Newest First' }
  ],
  PRICE_RANGES: [
    { label: 'Under ₹500', min: 0, max: 500 },
    { label: '₹500 - ₹1000', min: 500, max: 1000 },
    { label: '₹1000 - ₹2000', min: 1000, max: 2000 },
    { label: '₹2000 - ₹5000', min: 2000, max: 5000 },
    { label: 'Above ₹5000', min: 5000, max: Infinity }
  ],
};
