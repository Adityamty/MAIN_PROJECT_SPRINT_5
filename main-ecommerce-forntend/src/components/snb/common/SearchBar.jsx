import React, { useState, useEffect } from 'react';
import { Search, X } from 'lucide-react';
import { motion, AnimatePresence } from 'framer-motion';
import { useDebounce } from '../../../hooks/snb/useDebounce';

const SearchBar = ({ onSearch, placeholder = "Search products, brands, categories..." }) => {
  const [query, setQuery] = useState('');
  const [isFocused, setIsFocused] = useState(false);
  const debouncedQuery = useDebounce(query, 300);

  useEffect(() => {
    onSearch(debouncedQuery);
  }, [debouncedQuery, onSearch]);

  const clearSearch = () => {
    setQuery('');
    onSearch('');
  };

  return (
    <motion.div 
      className="relative w-full max-w-lg"
      initial={{ opacity: 0, y: -10 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ duration: 0.3 }}
    >
      <div className={`relative flex items-center transition-all duration-300 ${
        isFocused ? 'transform scale-[1.02]' : ''
      }`}>
        {/* Search Input */}
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => setIsFocused(true)}
          onBlur={() => setIsFocused(false)}
          placeholder={placeholder}
          className={`
            w-full pl-4 pr-14 py-3 text-sm font-medium
            border-2 rounded-full transition-all duration-300 
            focus:outline-none
            placeholder:text-gray-400 dark:placeholder:text-gray-500
            text-gray-900 dark:text-white
            ${isFocused 
              ? 'border-blue-500 bg-white dark:bg-gray-800 shadow-lg ring-4 ring-blue-100 dark:ring-blue-900/50' 
              : 'border-gray-200 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 hover:bg-white dark:hover:bg-gray-600 hover:border-gray-300 dark:hover:border-gray-500'
            }
          `}
        />

        {/* Search Icon */}
        <div className="absolute right-14 top-1/2 transform -translate-y-1/2 z-10 pointer-events-none">
          <Search className="w-5 h-5 text-gray-400 dark:text-gray-500" />
        </div>

        {/* Clear Button */}
        <div className="absolute right-3 flex items-center">
          <AnimatePresence>
            {query && (
              <motion.button
                initial={{ opacity: 0, scale: 0.8 }}
                animate={{ opacity: 1, scale: 1 }}
                exit={{ opacity: 0, scale: 0.8 }}
                transition={{ duration: 0.2 }}
                onClick={clearSearch}
                className="p-1 hover:bg-gray-100 dark:hover:bg-gray-600 rounded-full transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                aria-label="Clear search"
              >
                <X className="w-4 h-4 text-gray-400 dark:text-gray-500 hover:text-gray-600 dark:hover:text-gray-300" />
              </motion.button>
            )}
          </AnimatePresence>
        </div>
      </div>

      {/* Search Results Indicator */}
      {query && (
        <motion.div
          initial={{ opacity: 0, height: 0 }}
          animate={{ opacity: 1, height: 'auto' }}
          exit={{ opacity: 0, height: 0 }}
          className="absolute top-full left-0 right-0 mt-1 text-xs text-gray-500 dark:text-gray-400"
        >
          <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-600 rounded-lg px-3 py-2 shadow-sm">
            Searching for "{query}"...
          </div>
        </motion.div>
      )}
    </motion.div>
  );
};

export default SearchBar;
