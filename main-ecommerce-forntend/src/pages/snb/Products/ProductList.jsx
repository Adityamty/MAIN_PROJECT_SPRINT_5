import React, { useEffect, useState, useMemo, useCallback } from 'react';
import { useSearchParams } from 'react-router-dom';
import { motion } from 'framer-motion';
import { useProducts } from '../../../hooks/snb/useProducts';
import { useCategories } from '../../../hooks/snb/useCategories';
import ProductGrid from '../../../components/snb/common/ProductGrid';
import SortDropdown from '../../../components/snb/common/SortDropdown';
import Pagination from '../../../components/snb/common/Pagination';
import toast from 'react-hot-toast';

import TopHeader from '../../../components/snb/headers/TopHeader';
import MainHeader from '../../../components/snb/headers/MainHeader';
import FilterPanel from '../../../components/snb/common/FilterPanel';
import Footer from '../../../components/snb/Footer';
import BreadCrumbHeader from '../../../components/snb/headers/BreadCrumbHeader';

const ProductList = () => {
  const [searchParams] = useSearchParams();
  const [viewMode, setViewMode] = useState('grid');
  const [sortBy, setSortBy] = useState('name-asc');
  const [searchQuery, setSearchQuery] = useState('');
  const [currentPage, setCurrentPage] = useState(1);323.
  const [itemsPerPage] = useState(12);
  const [filters, setFilters] = useState({
    category: searchParams.get('category') ? [searchParams.get('category')] : [],
    priceMin: null,
    priceMax: null,
    size: ''
  });

  useEffect(() => {
    const queryFromUrl = searchParams.get('query');
    if (queryFromUrl) {
      setSearchQuery(queryFromUrl);
    }
  }, [searchParams]);

  const { products, loading, error } = useProducts({
    search: searchQuery,
    minPrice: filters.priceMin,
    maxPrice: filters.priceMax
  });

  const { categories } = useCategories();

  useEffect(() => {
    const categoryFromUrl = searchParams.get('category');
    if (categoryFromUrl) {
      setFilters((prev) => ({ ...prev, category: [categoryFromUrl] }));
    }
  }, [searchParams]);

  const transformedProducts = Array.isArray(products)
    ? products.map((product) => {
        const attr = product.attributes?.[0] || {};
        const allSizes = product.attributes?.map((attr) => attr.size).filter(Boolean) || [];
        const uniqueSizes = [...new Set(allSizes)];

        return {
          id: product.id,
          name: product.name,
          price: attr.price || 0,
          image: attr.productImage || '/placeholder.png',
          category: product.categoryName || '',
          size: attr.size || '',
          availableSizes: uniqueSizes,
          status: product.status,
          lastModifiedDate: product.lastModifiedDate,
          attributes: product.attributes || []
        };
      })
    : [];

  const filteredProducts = useMemo(() => {
    let filtered = transformedProducts.filter((product) => {
      const matchesSearch =
        !searchQuery ||
        product.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        product.category.toLowerCase().includes(searchQuery.toLowerCase()) ||
        product.id.toString().includes(searchQuery.toLowerCase());

      const matchesCategory =
        !filters.category ||
        filters.category.length === 0 ||
        filters.category.some((cat) => product.category.toLowerCase() === cat.toLowerCase());

      const matchesSize = !filters.size || product.availableSizes.includes(filters.size);

      return matchesSearch && matchesCategory && matchesSize;
    });

    filtered.sort((a, b) => {
      switch (sortBy) {
        case 'name-asc':
          return a.name.localeCompare(b.name);
        case 'name-desc':
          return b.name.localeCompare(a.name);
        case 'price-asc':
          return a.price - b.price;
        case 'price-desc':
          return b.price - a.price;
        case 'newest':
          return new Date(b.lastModifiedDate) - new Date(a.lastModifiedDate);
        case 'oldest':
          return new Date(a.lastModifiedDate) - new Date(b.lastModifiedDate);
        default:
          return 0;
      }
    });

    return filtered;
  }, [transformedProducts, searchQuery, sortBy, filters.category, filters.size]);

  const totalPages = Math.ceil(filteredProducts.length / itemsPerPage);
  const paginatedProducts = useMemo(() => {
    const startIndex = (currentPage - 1) * itemsPerPage;
    return filteredProducts.slice(startIndex, startIndex + itemsPerPage);
  }, [filteredProducts, currentPage, itemsPerPage]);

  useEffect(() => {
    setCurrentPage(1);
  }, [searchQuery, filters.category, filters.size, sortBy]);

  const handleSortChange = useCallback((newSortBy) => {
    setSortBy(newSortBy);
  }, []);

  const handlePageChange = useCallback((page) => {
    setCurrentPage(page);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }, []);

  const handleFilterChange = useCallback((key, value) => {
    setFilters((prev) => ({
      ...prev,
      [key]: value
    }));
  }, []);

  const handleClearFilters = useCallback(() => {
    setFilters({
      category: '',
      priceMin: null,
      priceMax: null,
      size: ''
    });
    setSearchQuery('');
  }, []);

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
        <div className="text-center">
          <div className="w-16 h-16 mx-auto mb-4 bg-red-100 dark:bg-red-900 rounded-full flex items-center justify-center">
            <span className="text-2xl">⚠️</span>
          </div>
          <h2 className="text-xl font-semibold text-gray-900 dark:text-white mb-2">
            Something went wrong
          </h2>
          <p className="text-gray-600 dark:text-gray-300 mb-4">{error}</p>
          <button onClick={() => window.location.reload()} className="btn-primary">
            Try Again
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900 transition-colors duration-300">
      <TopHeader />
      <MainHeader />
<BreadCrumbHeader/>
      <motion.div
        initial={{ opacity: 0, y: -20 }}
        animate={{ opacity: 1, y: 0 }}
        className="bg-white dark:bg-gray-800 shadow-sm sticky top-0 z-30 transition-colors duration-300"
      >
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <h2 className="text-xl font-semibold text-gray-900 dark:text-white">All Products</h2>
        </div>
      </motion.div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 flex gap-8">
        <div className="hidden lg:block w-64 sticky top-[5rem] h-fit">
          <FilterPanel
            isOpen={true}
            onClose={() => {}}
            categories={categories}
            filters={filters}
            onFilterChange={handleFilterChange}
            onClearFilters={handleClearFilters}
          />
        </div>

        <div className="flex-1">
          <div className="mb-6 flex justify-end items-center gap-4">
            <span className="text-sm text-gray-600 dark:text-gray-400">Sort by:</span>
            <SortDropdown sortBy={sortBy} onSortChange={handleSortChange} />
          </div>

          <ProductGrid
            products={paginatedProducts}
            loading={loading}
            viewMode={viewMode}
            onViewModeChange={setViewMode}
          />

          <Pagination
            currentPage={currentPage}
            totalPages={totalPages}
            totalItems={filteredProducts.length}
            itemsPerPage={itemsPerPage}
            onPageChange={handlePageChange}
          />
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default ProductList;
