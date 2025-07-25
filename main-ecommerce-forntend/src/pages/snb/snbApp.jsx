import React, { useEffect, Suspense, lazy, Component } from 'react';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';

import '../../index.css';
import '../../styles/snb/globals.css';
import '../../styles/snb/animations.css';
import Register from './Registration';
import Login from './Login';
import Home from './Home';

// Lazy load components for better performance
const ProductList = lazy(() => import('./Products/ProductList'));
const ProductDetail = lazy(() => import('./Products/ProductDetail'));

// Helper function to fix image URLs
function getImageUrl(url) {
  if (!url) return "";
  if (/^https?:\/\//.test(url)) return url;
  if (url.startsWith("assets/")) return `/${url}`;
  return url;
}

// Custom Error Boundary Component
class ErrorBoundary extends Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true, error };
  }

  componentDidCatch(error, errorInfo) {
    console.error('Application Error:', error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="min-h-screen flex items-center justify-center bg-secondary">
          <div className="text-center p-8 max-w-md">
            <div className="w-20 h-20 mx-auto mb-6 bg-red-100 dark:bg-red-900 rounded-full flex items-center justify-center">
              <svg className="w-10 h-10 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
              </svg>
            </div>
            <h2 className="text-2xl font-bold text-primary mb-4">Oops! Something went wrong</h2>
            <p className="text-secondary mb-6">
              We apologize for the inconvenience. Please try refreshing the page.
            </p>
            <div className="space-y-3">
              <button
                onClick={() => this.setState({ hasError: false, error: null })}
                className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition-colors duration-200"
              >
                Try Again
              </button>
              <button
                onClick={() => window.location.href = '/'}
                className="w-full bg-tertiary hover:bg-gray-300 text-secondary font-medium py-2 px-4 rounded-lg transition-colors duration-200"
              >
                Go to Homepage
              </button>
            </div>
          </div>
        </div>
      );
    }

    return this.props.children;
  }
}

// Loading component for Suspense fallback
function PageLoader() {
  return (
    <div className="min-h-screen flex items-center justify-center bg-secondary">
      <div className="text-center">
        <LoadingSpinner size="xl" className="mx-auto mb-4" />
        <p className="text-secondary font-medium">Loading Fashionhub...</p>
      </div>
    </div>
  );
}

// Page tracking hook for analytics
function usePageTracking() {
  const location = useLocation();

  useEffect(() => {
    const getPageTitle = (pathname) => {
      switch (pathname) {
        case '/':
        case '/products':
          return 'FashionHub - Fashion & Lifestyle Products';
        case pathname.match(/\/products\/\d+/)?.input:
          return 'Product Details - FashionHub';
        default:
          return 'FashionHub - Fashion & Lifestyle';
      }
    };
    
    document.title = getPageTitle(location.pathname);
  }, [location]);
}

// Main App Router Component
function AppRouter() {
  usePageTracking();

  return (
    <Suspense fallback={<PageLoader />}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login/>} />
        <Route path="/signup" element={<Register/>} />
        <Route path="/products" element={<ProductList />} />
        <Route path="/products/:id" element={<ProductDetail />} />
        
        {/* 404 Route */}
        <Route path="*" element={
          <div className="min-h-screen flex items-center justify-center bg-secondary">
            <div className="text-center">
              <h1 className="text-6xl font-bold text-muted mb-4">404</h1>
              <h2 className="text-2xl font-semibold text-primary mb-2">Page Not Found</h2>
              <p className="text-secondary mb-6">The page you're looking for doesn't exist.</p>
              <a 
                href="/" 
                className="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-6 rounded-lg transition-colors duration-200"
              >
                Go to Homepage
              </a>
            </div>
          </div>
        } />
      </Routes>
    </Suspense>
  );
}

// Main App Component
function snbApp() {
  useEffect(() => {
    // Initialize theme system
    const initializeTheme = () => {
      const savedTheme = localStorage.getItem('stylesphere-theme');
      const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
      const shouldBeDark = savedTheme === 'dark' || (!savedTheme && prefersDark);
      
      if (shouldBeDark) {
        document.documentElement.classList.add('dark');
        document.documentElement.style.colorScheme = 'dark';
      } else {
        document.documentElement.classList.remove('dark');
        document.documentElement.style.colorScheme = 'light';
      }
    };

    initializeTheme();
    console.log('Fashion E-commerce App initialized');
  }, []); 

  return (
    <ErrorBoundary>
      
        <div className="min-h-screen bg-secondary font-inter">
          {/* Global Toast Notifications */}
          <Toaster 
            position="top-right"
            toastOptions={{
              duration: 3000,
              style: {
                background: 'var(--bg-primary)',
                color: 'var(--text-primary)',
                borderRadius: '0.5rem',
                fontSize: '0.875rem',
                fontWeight: '500',
                border: '1px solid var(--border-primary)',
                boxShadow: '0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)',
              },
              success: {
                iconTheme: {
                  primary: '#10b981',
                  secondary: 'var(--bg-primary)',
                },
              },
              error: {
                iconTheme: {
                  primary: '#ef4444',
                  secondary: 'var(--bg-primary)',
                },
              },
            }}
          />
          
          {/* Main Application Routes */}
          <AppRouter />
        </div>
      
    </ErrorBoundary>
  );
}

export default snbApp;
export { getImageUrl };
