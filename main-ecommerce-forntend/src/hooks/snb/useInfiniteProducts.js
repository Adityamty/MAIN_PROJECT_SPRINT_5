import { useEffect, useState } from 'react';
import { productService } from '../../services/snb/productService';

export const useInfiniteProducts = (filters = {}, pageSize = 12) => {
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchNextPage = async () => {
    if (loading || !hasMore) return;

    setLoading(true);
    try {
      const response = await productService.getProducts({
        ...filters,
        page,
        size: pageSize,
      });

      const newProducts = response.products || [];

      setProducts((prev) => [...prev, ...newProducts]);
      setHasMore(newProducts.length === pageSize); // assume no more if fewer than pageSize
      setPage((prev) => prev + 1);
    } catch (err) {
      setError(err.message || 'Failed to fetch products');
    } finally {
      setLoading(false);
    }
  };

  // Reset when filters change
  const reset = async () => {
    setPage(1);
    setHasMore(true);
    setProducts([]);
    setError(null);
    try {
      const response = await productService.getProducts({
        ...filters,
        page: 1,
        size: pageSize,
      });
      setProducts(response.products || []);
      setHasMore((response.products || []).length === pageSize);
      setPage(2);
    } catch (err) {
      setError(err.message || 'Failed to fetch products');
    }
  };

  // When filters change
  useEffect(() => {
    reset();
  }, [JSON.stringify(filters)]);

  return { products, loading, error, fetchNextPage, hasMore, reset };
};
