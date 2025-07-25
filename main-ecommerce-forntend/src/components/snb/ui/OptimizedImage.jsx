// src/components/ui/OptimizedImage.jsx
import React, { useState, useCallback } from 'react';
import { motion } from 'framer-motion';

const OptimizedImage = ({ 
  src, 
  alt, 
  className = '', 
  placeholder = 'bg-gray-200',
  ...props 
}) => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [hasError, setHasError] = useState(false);

  const handleLoad = useCallback(() => {
    setIsLoaded(true);
  }, []);

  const handleError = useCallback(() => {
    setHasError(true);
    setIsLoaded(true);
  }, []);

  return (
    <div className={`relative overflow-hidden ${className}`}>
      {!isLoaded && (
        <div className={`absolute inset-0 ${placeholder} animate-pulse`} />
      )}
      
      <motion.img
        src={hasError ? '/images/product-placeholder.png' : src}
        alt={alt}
        onLoad={handleLoad}
        onError={handleError}
        initial={{ opacity: 0 }}
        animate={{ opacity: isLoaded ? 1 : 0 }}
        transition={{ duration: 0.3 }}
        className={`w-full h-full object-cover ${className}`}
        loading="lazy"
        {...props}
      />
    </div>
  );
};

export default OptimizedImage;
