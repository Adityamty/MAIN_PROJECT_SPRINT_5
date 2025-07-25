// Formatting utilities for StyleSphere

// Price formatting
export const formatPrice = (price, includeSymbol = true) => {
  if (typeof price !== 'number' || isNaN(price)) return includeSymbol ? '₹0' : '0';
  
  const formatted = price.toLocaleString('en-IN', {
    minimumFractionDigits: 0,
    maximumFractionDigits: 0
  });
  
  return includeSymbol ? `₹${formatted}` : formatted;
};

// Format discount
export const formatDiscount = (discount) => {
  if (typeof discount !== 'number' || discount <= 0) return '';
  return `${Math.round(discount)}% OFF`;
};

// Format product name for display
export const formatProductName = (name, maxLength = 50) => {
  if (!name) return '';
  if (name.length <= maxLength) return name;
  return name.substring(0, maxLength).trim() + '...';
};

// Format category name
export const formatCategoryName = (category) => {
  if (!category) return '';
  return category.charAt(0).toUpperCase() + category.slice(1).toLowerCase();
};

// Format size display
export const formatSize = (size) => {
  if (!size) return '';
  return size.toString().toUpperCase();
};

// Format stock status
export const formatStockStatus = (stockQuantity, isActive = true) => {
  if (!isActive) return 'Unavailable';
  if (stockQuantity === 0) return 'Out of Stock';
  if (stockQuantity < 5) return 'Limited Stock';
  return 'In Stock';
};

// Format review count
export const formatReviewCount = (count) => {
  if (!count || count === 0) return 'No reviews';
  if (count === 1) return '1 review';
  if (count < 1000) return `${count} reviews`;
  return `${(count / 1000).toFixed(1)}k reviews`;
};

// Format rating
export const formatRating = (rating, includeText = false) => {
  if (typeof rating !== 'number') return includeText ? 'No rating' : '0.0';
  const formatted = rating.toFixed(1);
  return includeText ? `${formatted} stars` : formatted;
};

// Format product code/SKU
export const formatSKU = (sku) => {
  if (!sku) return '';
  return sku.toUpperCase();
};

// Format search query for display
export const formatSearchQuery = (query) => {
  if (!query) return '';
  return query.trim().replace(/\s+/g, ' ');
};

// Format file size
export const formatFileSize = (bytes) => {
  if (bytes === 0) return '0 Bytes';
  
  const k = 1024;
  const sizes = ['Bytes', 'KB', 'MB', 'GB'];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
};

// Format phone number for display
export const formatPhoneDisplay = (phone) => {
  if (!phone) return '';
  const cleaned = phone.replace(/\D/g, '');
  
  if (cleaned.length === 10) {
    return `+91 ${cleaned.slice(0, 5)} ${cleaned.slice(5)}`;
  }
  
  return phone;
};

// Format address for display
export const formatAddress = (address) => {
  if (!address) return '';
  
  const parts = [
    address.street,
    address.city,
    address.state,
    address.pincode
  ].filter(Boolean);
  
  return parts.join(', ');
};

// Format order status
export const formatOrderStatus = (status) => {
  const statusMap = {
    'pending': 'Order Placed',
    'confirmed': 'Confirmed',
    'processing': 'Processing',
    'shipped': 'Shipped',
    'delivered': 'Delivered',
    'cancelled': 'Cancelled',
    'returned': 'Returned'
  };
  
  return statusMap[status.toLowerCase()] || status;
};

// Format delivery time
export const formatDeliveryTime = (days) => {
  if (!days || days <= 0) return 'Same day delivery';
  if (days === 1) return 'Next day delivery';
  if (days <= 7) return `${days} days delivery`;
  return `${Math.ceil(days / 7)} weeks delivery`;
};

// Format warranty period
export const formatWarranty = (months) => {
  if (!months || months <= 0) return 'No warranty';
  if (months < 12) return `${months} months warranty`;
  const years = Math.floor(months / 12);
  const remainingMonths = months % 12;
  
  if (remainingMonths === 0) {
    return years === 1 ? '1 year warranty' : `${years} years warranty`;
  }
  
  return `${years} year${years > 1 ? 's' : ''} ${remainingMonths} month${remainingMonths > 1 ? 's' : ''} warranty`;
};

// Format percentage
export const formatPercentage = (value, decimals = 0) => {
  if (typeof value !== 'number') return '0%';
  return `${value.toFixed(decimals)}%`;
};

// Format currency input (removes non-numeric characters)
export const formatCurrencyInput = (value) => {
  return value.replace(/[^\d.]/g, '').replace(/(\..*)\./g, '$1');
};

// Format search suggestions
export const formatSearchSuggestion = (suggestion, query) => {
  if (!query) return suggestion;
  
  const regex = new RegExp(`(${query})`, 'gi');
  return suggestion.replace(regex, '<mark>$1</mark>');
};
