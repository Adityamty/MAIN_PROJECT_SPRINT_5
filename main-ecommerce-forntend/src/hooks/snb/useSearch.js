import { useState, useCallback } from 'react';
import { useDebounce } from './useDebounce';

export const useSearch = (initialQuery = '', onSearchChange = null) => {
  const [query, setQuery] = useState(initialQuery);
  const [searchHistory, setSearchHistory] = useState([]);
  const debouncedQuery = useDebounce(query, 300);

  const updateQuery = useCallback((newQuery) => {
    setQuery(newQuery);
    
    // Add to search history if not empty and not already present
    if (newQuery.trim() && !searchHistory.includes(newQuery.trim())) {
      setSearchHistory(prev => [newQuery.trim(), ...prev.slice(0, 4)]);
    }
  }, [searchHistory]);

  const clearQuery = useCallback(() => {
    setQuery('');
  }, []);

  const clearHistory = useCallback(() => {
    setSearchHistory([]);
  }, []);

  // Call the callback when debounced query changes
  React.useEffect(() => {
    if (onSearchChange) {
      onSearchChange(debouncedQuery);
    }
  }, [debouncedQuery, onSearchChange]);

  return {
    query,
    debouncedQuery,
    searchHistory,
    updateQuery,
    clearQuery,
    clearHistory
  };
};

export default useSearch;
