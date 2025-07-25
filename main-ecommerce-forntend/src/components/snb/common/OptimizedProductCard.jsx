// src/components/common/OptimizedProductCard.jsx
import React, { memo, useMemo, useCallback } from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { getImageUrl } from '../../App';

const OptimizedProductCard = memo(({ product, onAddToCart }) => {
  // Memoize expensive calculations
  const discountPercentage = useMemo(() => {
    if (!product.mrp || !product.price) return null;
    return Math.round(((product.mrp - product.price) / product.mrp) * 100);
  }, [product.mrp, product.price]);

  // Memoize callback functions to prevent unnecessary re-renders
  const handleAddToCart = useCallback(() => {
    onAddToCart(product);
  }, [product, onAddToCart]);

  const imageUrl = useMemo(() => getImageUrl(product.imageUrl), [product.imageUrl]);

  return (
    <motion.div
      layout
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="product-card"
    >
      {/* Card content with optimized rendering */}
      <div className="relative aspect-square overflow-hidden bg-gray-100">
        <Link to={`/products/${product.id}`}>
          <img
            src={imageUrl}
            alt={product.name}
            loading="lazy"
            className="w-full h-full object-cover transition-transform duration-300 hover:scale-105"
          />
        </Link>
        
        {discountPercentage && (
          <div className="absolute top-3 left-3 bg-red-500 text-white px-2 py-1 rounded text-sm font-semibold">
            {discountPercentage}% OFF
          </div>
        )}
      </div>
      
      <div className="p-4">
        <Link to={`/products/${product.id}`}>
          <h3 className="font-semibold text-gray-900 hover:text-blue-600 transition-colors">
            {product.name}
          </h3>
        </Link>
        
        <div className="mt-2 flex items-center justify-between">
          <span className="text-xl font-bold text-gray-900">₹{product.price}</span>
          <button
            onClick={handleAddToCart}
            className="btn-primary"
          >
            Add to Cart
          </button>
        </div>
      </div>
    </motion.div>
  );
});

OptimizedProductCard.displayName = 'OptimizedProductCard';

export default OptimizedProductCard;
