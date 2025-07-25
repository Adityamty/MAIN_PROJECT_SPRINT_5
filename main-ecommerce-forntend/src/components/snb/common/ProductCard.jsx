import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import { Heart, ShoppingCart, Eye, Star } from 'lucide-react';
import Badge from '../ui/Badge';
import Button from '../ui/Button';
 
const ProductCard = ({ product, viewMode = 'grid' }) => {
  const [isImageLoaded, setIsImageLoaded] = useState(false);
  const [isFavorite, setIsFavorite] = useState(false);
  const [isHovered, setIsHovered] = useState(false);
 
  const discountPercentage = product.mrp && product.price
    ? Math.round(((product.mrp - product.price) / product.mrp) * 100)
    : null;
 
  const cardVariants = {
    hidden: { opacity: 0, y: 20 },
    visible: {
      opacity: 1,
      y: 0,
      transition: { duration: 0.3, ease: 'easeOut' }
    },
    hover: {
      y: -8,
      transition: { duration: 0.2, ease: 'easeInOut' }
    }
  };
 
  const imageVariants = {
    hidden: { scale: 1 },
    hover: {
      scale: 1.1,
      transition: { duration: 0.3, ease: 'easeInOut' }
    }
  };
 
  if (viewMode === 'list') {
    return (
      <motion.div
        variants={cardVariants}
        initial="hidden"
        animate="visible"
        whileHover="hover"
        className="bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden hover:shadow-xl transition-shadow duration-300"
      >
        <div className="flex">
          <div className="relative w-48 h-48 overflow-hidden">
            <Link to={`/products/${product.id}`}>
              <motion.img
                src={product.image}
                alt={product.name}
                className="w-full h-full object-cover"
                variants={imageVariants}
                onLoad={() => setIsImageLoaded(true)}
              />
            </Link>
            {discountPercentage && (
              <Badge
                variant="destructive"
                size="sm"
                className="absolute top-2 left-2"
              >
                {discountPercentage}% OFF
              </Badge>
            )}
          </div>
 
          <div className="flex-1 p-6 flex flex-col justify-between">
            <div>
              <Link to={`/products/${product.id}`}>
                <h3 className="font-semibold text-lg text-gray-900 dark:text-white hover:text-blue-600 transition-colors duration-200 line-clamp-2">
                  {product.name}
                </h3>
              </Link>
              <p className="text-gray-600 dark:text-gray-300 text-sm mt-1">{product.category}</p>
              {product.size && (
                <Badge variant="outline" size="sm" className="mt-2">
                  Size: {product.size}
                </Badge>
              )}
            </div>
 
            <div className="flex items-center justify-between mt-4">
              <div className="flex items-center space-x-2">
                <span className="text-2xl font-bold text-gray-900 dark:text-white">₹{product.price}</span>
                {product.mrp && product.mrp > product.price && (
                  <span className="text-lg text-gray-500 dark:text-gray-400 line-through">₹{product.mrp}</span>
                )}
              </div>
 
              <div className="flex items-center space-x-2">
                <Link to={`/products/${product.id}`}>
                  <Button
                    variant="outline"
                    size="sm"
                    className="border-black text-black hover:bg-[#8B4513] hover:text-white hover:border-[#8B4513] transition-colors duration-300"
                  >
                    <Eye className="w-4 h-4" />
                  </Button>
                </Link>
                <Button variant="primary" size="sm">
                  <ShoppingCart className="w-4 h-4 mr-1" />
                  Add to Cart
                </Button>
              </div>
            </div>
          </div>
        </div>
      </motion.div>
    );
  }
 
  return (
    <motion.div
      variants={cardVariants}
      initial="hidden"
      animate="visible"
      whileHover="hover"
      onHoverStart={() => setIsHovered(true)}
      onHoverEnd={() => setIsHovered(false)}
      className="group bg-white dark:bg-gray-800 rounded-lg shadow-sm border border-gray-100 dark:border-gray-700 overflow-hidden hover:shadow-2xl transition-all duration-300 h-full flex flex-col"
    >
      {/* Image Container */}
      <div className="relative aspect-square overflow-hidden bg-gray-100 dark:bg-gray-700">
        <Link to={`/products/${product.id}`}>
          <motion.img
            src={product.image}
            alt={product.name}
            className="w-full h-full object-cover transition-transform duration-300"
            variants={imageVariants}
            onLoad={() => setIsImageLoaded(true)}
            style={{ display: isImageLoaded ? 'block' : 'none' }}
          />
        </Link>
 
        {!isImageLoaded && (
          <div className="absolute inset-0 bg-gradient-to-r from-gray-200 via-gray-300 to-gray-200 dark:from-gray-600 dark:via-gray-500 dark:to-gray-600 bg-[length:200%_100%] animate-shimmer" />
        )}
 
        {discountPercentage && (
          <Badge
            variant="destructive"
            size="sm"
            className="absolute top-3 left-3 z-10"
          >
            {discountPercentage}% OFF
          </Badge>
        )}
      </div>
 
      {/* Product Info */}
      <div className="p-4 space-y-3 flex-1 flex flex-col justify-between">
        <div>
          <Link to={`/products/${product.id}`}>
            <h3 className="font-semibold text-gray-900 dark:text-white hover:text-blue-600 transition-colors duration-200 line-clamp-2 group-hover:text-blue-600">
              {product.name}
            </h3>
          </Link>
          <p className="text-gray-600 dark:text-gray-300 text-sm">{product.category}</p>
          {product.availableSizes && product.availableSizes.length > 0 && (
            <div className="flex items-center gap-1 mt-2">
              <span className="text-xs text-gray-500 dark:text-gray-400">Sizes:</span>
              <div className="flex flex-wrap gap-1">
                {product.availableSizes.slice(0, 3).map((size, index) => (
                  <span
                    key={index}
                    className="px-2 py-1 bg-gray-100 dark:bg-gray-700 text-xs rounded-md text-gray-700 dark:text-gray-300"
                  >
                    {size}
                  </span>
                ))}
                {product.availableSizes.length > 3 && (
                  <span className="text-xs text-gray-500 dark:text-gray-400">+{product.availableSizes.length - 3} more</span>
                )}
              </div>
            </div>
          )}
        </div>
 
        <div className="flex items-center justify-between">
          <div className="flex items-center space-x-2">
            <span className="text-xl font-bold text-gray-900 dark:text-white">₹{product.price}</span>
            {product.mrp && product.mrp > product.price && (
              <span className="text-sm text-gray-500 dark:text-gray-400 line-through">₹{product.mrp}</span>
            )}
          </div>
 
          <motion.div
            whileHover={{ scale: 1.05 }}
            whileTap={{ scale: 0.95 }}
            className="opacity-0 group-hover:opacity-100 transition-opacity duration-200"
          >
            <Link to={`/products/${product.id}`}>
                <Button
                  size="sm"
                  className="!bg-black text-white border border-black rounded-md !hover:bg-[#8B4513] hover:border-[#8B4513] hover:text-white transition-colors duration-300"
                >
                  View Product
                </Button>
            </Link>

          </motion.div>
        </div>
      </div>
    </motion.div>
  );
};
 
export default ProductCard;
 
 