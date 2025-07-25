import React, { useState } from 'react';
import { motion } from 'framer-motion';
import { ChevronLeft, ChevronRight, ZoomIn } from 'lucide-react';
import { getImageUrl } from '../../../pages/snb/snbApp';
 
const ImageGallery = ({ images = [], productName = '' }) => {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [isZoomed, setIsZoomed] = useState(false);
  const [zoomPosition, setZoomPosition] = useState({ x: 0, y: 0 });
  const [zoomModalOpen, setZoomModalOpen] = useState(false);
 
  // Use product image or placeholder if images array is empty
  const galleryImages = images.length > 0 ? images : [images[0] || ''];
 
  const handleMouseMove = (e) => {
    if (!isZoomed) return;
    const rect = e.currentTarget.getBoundingClientRect();
    const x = ((e.clientX - rect.left) / rect.width) * 100;
    const y = ((e.clientY - rect.top) / rect.height) * 100;
    setZoomPosition({ x, y });
  };
 
  const nextImage = () => {
    setSelectedIndex((prev) => (prev + 1) % galleryImages.length);
  };
 
  const prevImage = () => {
    setSelectedIndex((prev) => (prev - 1 + galleryImages.length) % galleryImages.length);
  };
 
  return (
    <div className="space-y-4">
      {/* Main Image */}
      <div className="relative aspect-square overflow-hidden rounded-lg bg-gray-100 dark:bg-gray-700 group transition-colors duration-300">
        <motion.img
          key={selectedIndex}
          src={getImageUrl(galleryImages[selectedIndex])}
          alt={`${productName} - Image ${selectedIndex + 1}`}
          className="w-full h-full object-cover cursor-zoom-in"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          transition={{ duration: 0.3 }}
          onMouseMove={handleMouseMove}
          onMouseEnter={() => setIsZoomed(true)}
          onMouseLeave={() => setIsZoomed(false)}
          style={{
            transform: isZoomed ? 'scale(2)' : 'scale(1)',
            transformOrigin: `${zoomPosition.x}% ${zoomPosition.y}%`,
            transition: 'transform 0.5s cubic-bezier(0.4, 0, 0.2, 1)',
          }}
        />
 
        {/* Navigation Arrows */}
        {galleryImages.length > 1 && (
          <>
            <button
              onClick={prevImage}
              className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 dark:bg-gray-800/80 hover:bg-white dark:hover:bg-gray-700 rounded-full p-2 shadow-lg opacity-0 group-hover:opacity-100 transition-all duration-200"
            >
              <ChevronLeft className="w-5 h-5 text-gray-700 dark:text-gray-300" />
            </button>
            <button
              onClick={nextImage}
              className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 dark:bg-gray-800/80 hover:bg-white dark:hover:bg-gray-700 rounded-full p-2 shadow-lg opacity-0 group-hover:opacity-100 transition-all duration-200"
            >
              <ChevronRight className="w-5 h-5 text-gray-700 dark:text-gray-300" />
            </button>
          </>
        )}
 
        {/* Zoom Indicator Button */}
        <button
          type="button"
          className="absolute top-4 right-4 bg-black/50 dark:bg-white/20 text-white dark:text-gray-200 p-2 rounded-full transition-transform duration-200 hover:scale-110"
          aria-label="Zoom in"
          onClick={() => setZoomModalOpen(true)}
        >
          <ZoomIn className="w-5 h-5" />
        </button>
      </div>
 
      {/* Zoom Modal */}
      {zoomModalOpen && (
        <div
          className="fixed inset-0 z-50 flex items-center justify-center bg-black/80"
          onClick={() => setZoomModalOpen(false)}
          tabIndex={-1}
          aria-modal="true"
          role="dialog"
        >
          {/* Prevent closing when clicking the image or close button */}
          <div
            className="relative"
            onClick={e => e.stopPropagation()}
          >
            <button
              onClick={() => setZoomModalOpen(false)}
              className="absolute -top-6 -right-6 text-white hover:text-red-400 text-4xl font-bold z-50 bg-black/60 rounded-full w-12 h-12 flex items-center justify-center"
              title="Close"
              tabIndex={0}
            >
              &times;
            </button>
            <img
              src={getImageUrl(galleryImages[selectedIndex])}
              alt={`${productName} - Zoomed`}
              className="shadow-2xl rounded-2xl object-contain"
              style={{
                maxHeight: '99vh',
                maxWidth: '99vw',
                minHeight: '600px',
                minWidth: '600px',
                background: 'transparent',
                boxShadow: '0 8px 40px 8px rgba(0,0,0,0.7)',
                transition: 'transform 0.3s cubic-bezier(0.4, 0, 0.2, 1)'
              }}
            />
          </div>
        </div>
      )}
 
      {/* Thumbnail Navigation */}
      {galleryImages.length > 1 && (
        <div className="flex space-x-2 overflow-x-auto pb-2">
          {galleryImages.map((image, index) => (
            <motion.button
              key={index}
              onClick={() => setSelectedIndex(index)}
              className={`flex-shrink-0 w-20 h-20 rounded-lg overflow-hidden border-2 transition-colors duration-200 ${
                index === selectedIndex
                  ? 'border-blue-500 dark:border-blue-400'
                  : 'border-gray-200 dark:border-gray-600 hover:border-gray-300 dark:hover:border-gray-500'
              }`}
              whileHover={{ scale: 1.05 }}
              whileTap={{ scale: 0.95 }}
            >
              <img
                src={getImageUrl(image)}
                alt={`${productName} thumbnail ${index + 1}`}
                className="w-full h-full object-cover"
              />
            </motion.button>
          ))}
        </div>
      )}
    </div>
  );
};
 
export default ImageGallery;
 
 