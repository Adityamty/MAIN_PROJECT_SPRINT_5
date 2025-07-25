import React from 'react';
import StarIcon from '@mui/icons-material/Star';
import StarHalfIcon from '@mui/icons-material/StarHalf';
import StarBorderIcon from '@mui/icons-material/StarBorder';

const ProductItem = ({ product }) => {
  console.log('Product Item:', product);
  const renderStars = (rating) => {
    const stars = [];
    for (let i = 1; i <= 5; i++) {
      if (rating >= i) {
        stars.push(<StarIcon key={i} fontSize="small" />);
      } else if (rating >= i - 0.5) {
        stars.push(<StarHalfIcon key={i} fontSize="small" />);
      } else {
        stars.push(<StarBorderIcon key={i} fontSize="small" />);
      }
    }
    return stars;
  };

  return (
    <div
      className="bg-white rounded-xl shadow-sm p-3 relative transition duration-300 hover:border hover:border-red-500 hover:shadow-lg h-full flex flex-col justify-between"
    >
      {/* Discount Badge */}
      {product.discount && (
        <div className="absolute top-2 left-2 bg-red-600 text-white text-xs px-2 py-1 rounded-md z-10">
          -{product.discount}%
        </div>
      )}

      {/* Product Image */}
      <img
        src={product.image}
        alt={product.name}
        className="w-full h-44 object-contain"
      />

      {/* Product Info */}
      <div className="mt-4">
        <h3 className="text-sm font-semibold min-h-[40px]">{product.name}</h3>

        <div className="flex text-yellow-500 text-sm mt-1">
          {renderStars(product.rating || 5)}
        </div>

        <div className="text-base font-semibold mt-2">
          ${product.price.toFixed(2)}
          {product.originalPrice && (
            <span className="text-gray-400 text-sm line-through ml-2 font-normal">
              ${product.originalPrice.toFixed(2)}
            </span>
          )}
        </div>
      </div>

      {/* Add to Cart Button */}
      <button
        className="mt-4 bg-[#09002e] hover:bg-red-600 text-white text-sm font-medium py-2 px-4 rounded-full transition duration-300"
      >
        Add To Cart
      </button>
    </div>
  );
};

export default ProductItem;
