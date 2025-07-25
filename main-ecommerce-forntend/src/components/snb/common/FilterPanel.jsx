import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { ChevronDown, ChevronUp } from 'lucide-react';
import Button from '../ui/Button';

const FilterPanel = ({
  isOpen,
  onClose,
  categories = [],
  filters = {},
  onFilterChange,
  onClearFilters
}) => {
  const [expandedSections, setExpandedSections] = useState({
    category: true,
    price: true,
    size: true
  });

  const toggleSection = (section) => {
    setExpandedSections((prev) => ({
      ...prev,
      [section]: !prev[section]
    }));
  };

  const priceRanges = [
    { label: 'Under ₹500', min: 0, max: 500 },
    { label: '₹500 - ₹1000', min: 500, max: 1000 },
    { label: '₹1000 - ₹2000', min: 1000, max: 2000 },
    { label: '₹2000 - ₹5000', min: 2000, max: 5000 },
    { label: 'Above ₹5000', min: 5000, max: Infinity }
  ];

  const sizes = ['XS', 'S', 'M', 'L', 'XL', 'XXL'];

  const FilterSection = ({ title, isExpanded, onToggle, children }) => (
    <div className="border-b border-gray-200 dark:border-gray-700 pb-4 mb-4">
      <button
        onClick={onToggle}
        className="flex items-center justify-between w-full text-left font-medium text-gray-900 dark:text-white"
      >
        {title}
        {isExpanded ? <ChevronUp size={20} /> : <ChevronDown size={20} />}
      </button>
      <AnimatePresence>
        {isExpanded && (
          <motion.div
            initial={{ height: 0, opacity: 0 }}
            animate={{ height: 'auto', opacity: 1 }}
            exit={{ height: 0, opacity: 0 }}
            transition={{ duration: 0.2 }}
            className="mt-3 space-y-2"
          >
            {children}
          </motion.div>
        )}
      </AnimatePresence>
    </div>
  );

  return (
    <AnimatePresence>
      {isOpen && (
        <>
          {/* Mobile Overlay */}
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            className="fixed inset-0 bg-black bg-opacity-50 z-40 lg:hidden"
            onClick={onClose}
          />

          {/* Filter Panel */}
          <motion.div
  initial={{ x: -300, opacity: 0 }}
  animate={{ x: 0, opacity: 1 }}
  exit={{ x: -300, opacity: 0 }}
  transition={{ duration: 0.3, ease: 'easeInOut' }}
  className="
    fixed   // mobile: fixed on screen
    lg:static  // desktop: default flow
    top-[4rem] left-0 
    w-80 lg:w-64
    max-h-screen overflow-y-auto 
    bg-white dark:bg-gray-800 
    shadow-2xl lg:shadow-none 
    z-40 lg:z-0
    rounded-r-md
  "
>
            <div className="p-6 space-y-4">
              {/* Header */}
              <div className="flex items-center justify-between mb-2">
                <h2 className="text-xl font-bold text-gray-900 dark:text-white">Filters</h2>
                <button
                  onClick={onClose}
                  className="lg:hidden text-gray-500 hover:text-gray-700 dark:text-gray-400 dark:hover:text-gray-200"
                >
                  ✕
                </button>
              </div>

              {/* Category Filter */}
              <FilterSection
                title="Category"
                isExpanded={expandedSections.category}
                onToggle={() => toggleSection('category')}
              >
                <div className="space-y-2">
                  <label className="flex items-center cursor-pointer">
                    <input
                      type="checkbox"
                      name="category"
                      value=""
                      checked={!filters.category || filters.category.length === 0}
                      onChange={() => onFilterChange('category', '')}
                      className="w-4 h-4 text-blue-600 border-gray-300 dark:border-gray-600 dark:bg-gray-700 focus:ring-blue-500 dark:focus:ring-blue-600 focus:ring-2"
                    />
                    <span className="ml-2 text-sm text-gray-700 dark:text-gray-300">All Categories</span>
                  </label>
                  {categories.map((category) => (
                    <label key={category.id || category.categoryName} className="flex items-center cursor-pointer">
                      <input
                        type="checkbox"
                        name="category"
                        value={category.categoryName}
                        checked={
                          Array.isArray(filters.category)
                            ? filters.category.includes(category.categoryName)
                            : filters.category === category.categoryName
                        }
                        onChange={(e) => {
                          let newCategories = Array.isArray(filters.category)
                            ? [...filters.category]
                            : filters.category
                            ? [filters.category]
                            : [];
                          if (e.target.checked) {
                            newCategories.push(category.categoryName);
                          } else {
                            newCategories = newCategories.filter((c) => c !== category.categoryName);
                          }
                          onFilterChange('category', newCategories);
                        }}
                        className="w-4 h-4 text-blue-600 border-gray-300 dark:border-gray-600 dark:bg-gray-700 focus:ring-blue-500 dark:focus:ring-blue-600 focus:ring-2"
                      />
                      <span className="ml-2 text-sm text-gray-700 dark:text-gray-300 capitalize">
                        {category.categoryName}
                      </span>
                    </label>
                  ))}
                </div>
              </FilterSection>

              {/* Price Range Filter */}
              <FilterSection
                title="Price Range"
                isExpanded={expandedSections.price}
                onToggle={() => toggleSection('price')}
              >
                <div className="space-y-2">
                  {priceRanges.map((range) => (
                    <label key={range.label} className="flex items-center cursor-pointer">
                      <input
                        type="radio"
                        name="priceRange"
                        checked={
                          filters.priceMin === range.min &&
                          (range.max === Infinity ? filters.priceMax === null : filters.priceMax === range.max)
                        }
                        onChange={() => {
                          onFilterChange('priceMin', range.min);
                          onFilterChange('priceMax', range.max === Infinity ? null : range.max);
                        }}
                        className="w-4 h-4 text-blue-600 border-gray-300 dark:border-gray-600 dark:bg-gray-700 focus:ring-blue-500 dark:focus:ring-blue-600 focus:ring-2"
                      />
                      <span className="ml-2 text-sm text-gray-700 dark:text-gray-300">{range.label}</span>
                    </label>
                  ))}
                </div>
              </FilterSection>

              {/* Size Filter */}
              <FilterSection
                title="Size"
                isExpanded={expandedSections.size}
                onToggle={() => toggleSection('size')}
              >
                <div className="grid grid-cols-3 gap-2">
                  {sizes.map((size) => (
                    <button
                      key={size}
                      onClick={() => onFilterChange('size', filters.size === size ? '' : size)}
                      className={`px-3 py-2 text-sm border rounded-md transition-colors ${
                        filters.size === size
                          ? 'bg-blue-600 text-white border-blue-600'
                          : 'bg-white dark:bg-gray-700 text-gray-700 dark:text-gray-300 border-gray-300 dark:border-gray-600 hover:bg-gray-50 dark:hover:bg-gray-600'
                      }`}
                    >
                      {size}
                    </button>
                  ))}
                </div>
              </FilterSection>

              {/* Clear Filters Button */}
              <div className="pt-2">
                <Button onClick={onClearFilters} variant="outline" className="w-full">
                  Clear All Filters
                </Button>
              </div>
            </div>
          </motion.div>
        </>
      )}
    </AnimatePresence>
  );
};

export default FilterPanel;
