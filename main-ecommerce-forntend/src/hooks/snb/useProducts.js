import { useEffect, useState } from 'react';
import { productService } from '../../services/snb/productService';

export const useProducts = (options = {}) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalCount, setTotalCount] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    const fetchProducts = async () => {
      setLoading(true);
      setError(null);
      try {
        // Build search parameters
        let searchParams = {
          ...options,
          page: options.page || 1,
          size: options.size || 12
        };

        // If category is specified, search by category name
        if (options.category) {
          searchParams.search = options.category;
        }

        // If search query is provided, use it directly
        if (options.search) {
          searchParams.search = options.search;
        }

        const response = await productService.getProducts(searchParams);
        
        // Handle pagination response format
        if (response.products && Array.isArray(response.products)) {
          setProducts(response.products);
          setTotalCount(response.totalProductCount || 0);
          setTotalPages(response.totalPages || 0);
        } else if (Array.isArray(response)) {
          // Handle direct array response
          setProducts(response);
          setTotalCount(response.length);
          setTotalPages(1);
        } else {
          console.error('Unexpected response format:', response);
          setProducts([]);
          setTotalCount(0);
          setTotalPages(0);
        }
      } catch (err) {
        console.error('Error fetching products:', err);
        setError(err.message || 'Failed to fetch products');
        setProducts([]);
        setTotalCount(0);
        setTotalPages(0);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, [options.category, options.search, options.page, options.size, options.minPrice, options.maxPrice]);

  return { products, loading, error, totalCount, totalPages };
};
