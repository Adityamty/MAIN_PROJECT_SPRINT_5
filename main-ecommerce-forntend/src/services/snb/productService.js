import api from './api';

export const productService = {
  // Get all products with pagination and filtering
  getProducts: async (options = {}) => {
    try {
      const {
        page = 1,
        size = 12,
        status = 'active',
        search = '',
        categoryId = null,
        minPrice = null,
        maxPrice = null
      } = options;

      // Build URL without sortBy - we'll handle sorting client-side
      let url = `/products?page=${page}&size=${size}`;
      
      if (status) url += `&status=${status}`;
      if (search) url += `&search=${encodeURIComponent(search)}`;
      if (categoryId) url += `&categoryId=${categoryId}`;
      if (minPrice !== null) url += `&minPrice=${minPrice}`;
      if (maxPrice !== null) url += `&maxPrice=${maxPrice}`;

      const response = await api.get(url);
      return response.data;
    } catch (error) {
      console.error('Error fetching products:', error);
      throw error;
    }
  },

  // Client-side sorting function for dropdown filters
  sortProducts: (products, sortBy) => {
    const sortedProducts = [...products];
    
    switch (sortBy) {
      case 'name-asc':
        return sortedProducts.sort((a, b) => a.name.localeCompare(b.name));
      case 'name-desc':
        return sortedProducts.sort((a, b) => b.name.localeCompare(a.name));
      case 'price-asc':
        return sortedProducts.sort((a, b) => a.price - b.price);
      case 'price-desc':
        return sortedProducts.sort((a, b) => b.price - a.price);
      case 'newest':
        return sortedProducts.sort((a, b) => new Date(b.lastModifiedDate || b.createdAt) - new Date(a.lastModifiedDate || a.createdAt));
      case 'oldest':
        return sortedProducts.sort((a, b) => new Date(a.lastModifiedDate || a.createdAt) - new Date(b.lastModifiedDate || b.createdAt));
      default:
        return sortedProducts;
    }
  },

  // Get product by ID
  getProductById: async (id) => {
    try {
      const response = await api.get(`/products/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product by ID:', error);
      throw error;
    }
  },

  // Get product sizes
  getProductSizes: async (id) => {
    try {
      const response = await api.get(`/products/${id}/sizes`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product sizes:', error);
      throw error;
    }
  },

  // Get product SKUs
  getProductSkus: async (id, size = null) => {
    try {
      let url = `/products/${id}/skus`;
      if (size) {
        url += `?size=${encodeURIComponent(size)}`;
      }
      const response = await api.get(url);
      return response.data;
    } catch (error) {
      console.error('Error fetching product SKUs:', error);
      throw error;
    }
  },

  // Get product seller
  getProductSeller: async (id) => {
    try {
      const response = await api.get(`/products/${id}/seller`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product seller:', error);
      throw error;
    }
  },

  // Get products by category
  getProductsByCategory: async (categoryId) => {
    try {
      const response = await api.get(`/products/category/${categoryId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching products by category:', error);
      throw error;
    }
  },

  // Get product stats
  getProductStats: async () => {
    try {
      const response = await api.get('/products/stats');
      return response.data;
    } catch (error) {
      console.error('Error fetching product stats:', error);
      throw error;
    }
  },

  // Get all product IDs
  getAllProductIds: async () => {
    try {
      const response = await api.get('/products/ids');
      return response.data;
    } catch (error) {
      console.error('Error fetching product IDs:', error);
      throw error;
    }
  },

  // Get product attributes
  getProductAttributes: async (options = {}) => {
    try {
      const { category, brand, color, size } = options;
      let url = '/products/attributes';
      
      const params = new URLSearchParams();
      if (category) params.append('category', category);
      if (brand) params.append('brand', brand);
      if (color) params.append('color', color);
      if (size) params.append('size', size);
      
      if (params.toString()) {
        url += `?${params.toString()}`;
      }
      
      const response = await api.get(url);
      return response.data;
    } catch (error) {
      console.error('Error fetching product attributes:', error);
      throw error;
    }
  }
};
