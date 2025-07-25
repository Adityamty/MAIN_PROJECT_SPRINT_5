import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Grid, List } from 'lucide-react';
import ProductCard from './ProductCard';
import { ProductGridSkeleton } from '../ui/LoadingSpinner';
import Button from '../ui/Button';
 
const ProductGrid = ({
  products = [],
  loading = false,
  viewMode = 'grid',
  onViewModeChange,
  sortBy = 'name',
  onSortChange
}) => {
  const [currentPage, setCurrentPage] = useState(1);
  const productsPerPage = 12;
 
  // Use products directly without sorting (sorting is handled in parent component)
  const sortedProducts = products;
 
  // Pagination logic
  const totalPages = Math.ceil(sortedProducts.length / productsPerPage);
  const startIndex = (currentPage - 1) * productsPerPage;
  const currentProducts = sortedProducts.slice(startIndex, startIndex + productsPerPage);
 
  const handlePageChange = (page) => {
    setCurrentPage(page);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };
 
  if (loading) {
    return <ProductGridSkeleton />;
  }
 
  return (
    <div className="space-y-6">
      {/* Controls */}
      <div className="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
        {/* Results count */}
        <div className="text-sm text-gray-600 dark:text-gray-300">
          Showing {startIndex + 1}-{Math.min(startIndex + productsPerPage, sortedProducts.length)} of {sortedProducts.length} products
        </div>
 
        {/* View controls */}
        <div className="flex items-center space-x-4">
          {/* View mode toggle */}
          <div className="flex items-center border border-gray-300 dark:border-gray-600 rounded-lg bg-white dark:bg-gray-700">
            <button
              onClick={() => onViewModeChange('grid')}
              className={`p-2 rounded-l-lg transition-colors duration-200 ${
                viewMode === 'grid'
                  ? 'bg-[#a0522d] text-white'
                  : 'text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600'
              }`}
            >
              <Grid className="w-4 h-4" />
            </button>
            <button
              onClick={() => onViewModeChange('list')}
              className={`p-2 rounded-r-lg transition-colors duration-200 ${
                viewMode === 'list'
                  ? 'bg-[#a0522d] text-white'
                  : 'text-gray-600 dark:text-gray-300 hover:bg-gray-100 dark:hover:bg-gray-600'
              }`}
            >
              <List className="w-4 h-4" />
            </button>
          </div>
        </div>
      </div>
 
      {/* Products Grid */}
      <motion.div
        layout
        className={`grid gap-6 ${
          viewMode === 'grid'
            ? 'grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 auto-rows-fr'
            : 'grid-cols-1'
        }`}
      >
        {currentProducts.map((product, index) => (
          <motion.div
            key={product.id}
            layout
            initial={{ opacity: 0, y: 20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.3, delay: index * 0.05 }}
          >
            <ProductCard product={product} viewMode={viewMode} />
          </motion.div>
        ))}
      </motion.div>
 
      {/* Empty state */}
      {sortedProducts.length === 0 && !loading && (
        <motion.div
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          className="text-center py-12"
        >
          <div className="w-24 h-24 mx-auto mb-4 bg-gray-100 dark:bg-gray-700 rounded-full flex items-center justify-center">
            <Grid className="w-12 h-12 text-gray-400 dark:text-gray-500" />
          </div>
          <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-2">No products found</h3>
          <p className="text-gray-600 dark:text-gray-300">Try adjusting your search or filter criteria</p>
        </motion.div>
      )}
 
      {/* Pagination */}
      {totalPages > 1 && (
        <div className="flex justify-center items-center space-x-2 mt-8">
          <Button
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 1}
            variant="outline"
            size="sm"
            className="border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
          >
            Previous
          </Button>
         
          {[...Array(totalPages)].map((_, index) => {
            const page = index + 1;
            const isCurrentPage = page === currentPage;
           
            // Show first page, last page, current page, and pages around current
            const shouldShow =
              page === 1 ||
              page === totalPages ||
              (page >= currentPage - 1 && page <= currentPage + 1);
           
            if (!shouldShow && page === 2 && currentPage > 4) {
              return <span key={page} className="px-2 text-gray-400 dark:text-gray-500">...</span>;
            }
           
            if (!shouldShow && page === totalPages - 1 && currentPage < totalPages - 3) {
              return <span key={page} className="px-2 text-gray-400 dark:text-gray-500">...</span>;
            }
           
            if (!shouldShow) return null;
           
            return (
              <Button
                key={page}
                onClick={() => handlePageChange(page)}
                variant={isCurrentPage ? 'primary' : 'outline'}
                size="sm"
                className={`min-w-[40px] ${
                  !isCurrentPage
                    ? 'border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600'
                    : ''
                }`}
              >
                {page}
              </Button>
            );
          })}
         
          <Button
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage === totalPages}
            variant="outline"
            size="sm"
            className="border-gray-300 dark:border-gray-600 text-gray-700 dark:text-gray-300 bg-white dark:bg-gray-700 hover:bg-gray-50 dark:hover:bg-gray-600"
          >
            Next
          </Button>
        </div>
      )}
    </div>
  );
};
 
export default ProductGrid;
