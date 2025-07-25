import api from './api';

// JWT token decoding utility
const decodeJWT = (token) => {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error('Error decoding JWT token:', error);
    return null;
  }
};

// Check if user has customer role
const hasCustomerRole = (roles) => {
  if (!roles || !Array.isArray(roles)) return false;
  return roles.some(role => 
    role === 'customer' || 
    role === 'ROLE_customer' ||
    role.toLowerCase() === 'customer'
  );
};

export const authService = {
  // User registration
  signup: async (userData) => {
    try {
      const response = await api.post('/user/signup', {
        firstName: userData.firstName,
        lastName: userData.lastName,
        email: userData.email,
        password: userData.password,
        phoneNumber: userData.phone
      });
      return response.data;
    } catch (error) {
      console.error('Error during signup:', error);
      throw error;
    }
  },

  // User login
  login: async (credentials) => {
    try {
      const response = await api.post('/user/login', credentials);
      const { token, roles, ...userData } = response.data;
      
      // Decode JWT token to get additional role information
      const decodedToken = decodeJWT(token);
      const tokenRoles = decodedToken?.roles || [];
      
      // Combine roles from response and token
      const allRoles = [...(roles || []), ...tokenRoles];
      
      // Check if user has customer role
      if (!hasCustomerRole(allRoles)) {
        throw new Error('Access denied. You do not have customer role. Please contact administrator.');
      }
      
      // Store token and user data in localStorage
      if (token) {
        localStorage.setItem('authToken', token);
        localStorage.setItem('user', JSON.stringify({ ...userData, roles: allRoles }));
        
        // Set default authorization header for future requests
        api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      }
      
      return { ...response.data, roles: allRoles };
    } catch (error) {
      console.error('Error during login:', error);
      throw error;
    }
  },

  // Get current user
  getCurrentUser: async () => {
    try {
      const response = await api.get('/user/current');
      return response.data;
    } catch (error) {
      console.error('Error fetching current user:', error);
      throw error;
    }
  },

  // Get user basic info by ID
  getUserBasicInfo: async (userId) => {
    try {
      const response = await api.get(`/user/basic-info/${userId}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching user basic info:', error);
      throw error;
    }
  },

  // Logout
  logout: () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    delete api.defaults.headers.common['Authorization'];
  },

  // Check if user is authenticated and has customer role
  isAuthenticated: () => {
    const token = localStorage.getItem('authToken');
    const user = localStorage.getItem('user');
    
    if (!token || !user) return false;
    
    try {
      const userData = JSON.parse(user);
      const decodedToken = decodeJWT(token);
      
      // Check if token is expired
      if (decodedToken && decodedToken.exp * 1000 < Date.now()) {
        authService.logout();
        return false;
      }
      
      // Check if user has customer role
      const tokenRoles = decodedToken?.roles || [];
      const userRoles = userData?.roles || [];
      const allRoles = [...userRoles, ...tokenRoles];
      
      return hasCustomerRole(allRoles);
    } catch (error) {
      console.error('Error checking authentication:', error);
      return false;
    }
  },

  // Get stored user data
  getStoredUser: () => {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  },

  // Get stored token
  getStoredToken: () => {
    return localStorage.getItem('authToken');
  },

  // Decode JWT token
  decodeJWT: decodeJWT,

  // Check if user has customer role
  hasCustomerRole: hasCustomerRole
};

// Set up request interceptor to add auth token to all requests
api.interceptors.request.use(
  (config) => {
    const token = authService.getStoredToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Set up response interceptor to handle auth errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Clear auth data on unauthorized
      authService.logout();
      // Redirect to login if not already there
      if (!window.location.pathname.includes('/login')) {
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

export default authService;
