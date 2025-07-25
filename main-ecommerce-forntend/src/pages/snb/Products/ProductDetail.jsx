import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useProductDetails } from '../../../hooks/snb/useProductDetails';
import { ProductDetailSkeleton } from '../../../components/snb/ui/LoadingSpinner';
import Button from '../../../components/snb/ui/Button';
import ImageGallery from '../../../components/snb/common/ImageGallery';
import SizeChart from '../../../components/snb/common/SizeChart';
import toast from 'react-hot-toast';
import TopHeader from '../../../components/snb/headers/TopHeader';
import MainHeader from '../../../components/snb/headers/MainHeader';
import { Minus, Plus, Share2, Truck, RotateCcw, Ruler } from 'lucide-react';
import { motion } from 'framer-motion';
import BreadCrumbHeader from '../../../components/snb/headers/BreadCrumbHeader';

const ProductDetail = () => {
  const { id } = useParams();
  const { product, loading, error } = useProductDetails(id);
  const [selectedSize, setSelectedSize] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [currentPrice, setCurrentPrice] = useState(0);
  const [discountedPrice, setDiscountedPrice] = useState(0);
  const [showSizeChart, setShowSizeChart] = useState(false);

  const hardcodedPromotions = [
    {
      promoType: 'Flat ₹100 Off',
      promoCode: 'SAVE100',
      description: 'Get ₹100 off on orders above ₹999',
    },
    {
      promoType: 'Free Delivery',
      promoCode: 'FREESHIP',
      description: 'Free shipping on your first purchase',
    },
  ];

  useEffect(() => {
    if (!product) return;
    const attr = product.attributes?.[0];
    if (attr) {
      setSelectedSize(attr.size);
      setCurrentPrice(attr.price);
    }
  }, [product]);

  useEffect(() => {
    const discountPercent = 20;
    const discount = Math.round(currentPrice - (currentPrice * discountPercent) / 100);
    setDiscountedPrice(discount);
  }, [currentPrice]);

  const getPriceForSelectedSize = (size) => {
    return product?.attributes?.find((a) => a.size === size)?.price || 0;
  };

  const handleSizeSelect = (size) => {
    setSelectedSize(size);
    setCurrentPrice(getPriceForSelectedSize(size));
  };

  const handleAddToCart = async () => {
    const userId = sessionStorage.getItem('userId');
    if (!userId) {
      toast.error('User not logged in!');
      return;
    }

    if (!selectedSize) {
      toast.error('Please select a size');
      return;
    }

    const selectedAttr = product.attributes.find(attr => attr.size === selectedSize);
    const unitPrice = selectedAttr?.price || currentPrice;
    const discount = 100.00;
    const finalPrice = (unitPrice - discount) * quantity;

    const payload = {
      userId: parseInt(userId),
      productId: product.id,
      sku: selectedAttr?.sku || '',
      size: selectedSize,
      quantity,
      unitPrice,
      discount,
      finalPrice
    };

    try {
      const res = await fetch('http://localhost:8085/api/cart/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (res.ok) {
        const data = await res.json();
        console.log('Added to cart:', data);
        toast.success('Product added to cart!');
      } else {
        toast.error('Error adding to cart');
      }
    } catch (err) {
      console.error('Error:', err);
      toast.error('Network error');
    }
  };

  if (loading) return <ProductDetailSkeleton />;
  if (error || !product) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 dark:bg-gray-900">
        <div className="text-center">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-2">Product not found</h2>
          <p className="text-gray-600 dark:text-gray-300 mb-4">The product you're looking for doesn't exist.</p>
          <Link to="/products">
            <Button variant="primary">Browse Products</Button>
          </Link>
        </div>
      </div>
    );
  }

  const productImage = product.attributes?.[0]?.productImage || '/fallback.jpg';
  const allSizes = ['XS', 'S', 'M', 'L', 'XL', 'XXL'];
  const availableSizes = product.attributes.map(attr => attr.size);

  const features = [
    { icon: Truck, text: 'Free shipping on orders over ₹499' },
    { icon: RotateCcw, text: '7-14 days easy return policy' },
  ];

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      <TopHeader />
      <MainHeader />
      <BreadCrumbHeader />

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Image Section */}
        <motion.div initial={{ x: -20, opacity: 0 }} animate={{ x: 0, opacity: 1 }}>
          <ImageGallery images={[productImage]} productName={product.name} />
        </motion.div>

        {/* Product Info */}
        <motion.div initial={{ x: 20, opacity: 0 }} animate={{ x: 0, opacity: 1 }} className="space-y-6">
          <h3 className="text-5xl font-extrabold text-gray-900 dark:text-white mb-6 tracking-tight">
            {product.name}
          </h3>

          {/* Price Section */}
          <div className="text-xl font-bold text-green-700 space-y-1">
            <div className="flex items-center gap-2">
              <span className="text-gray-500 line-through text-base">₹{currentPrice.toLocaleString()}</span>
              <h1 className="text-2xl text-green-700 font-bold">₹{discountedPrice.toLocaleString()}</h1>
              <span className="text-sm text-red-600 font-semibold bg-red-100 px-2 py-0.5 rounded">20% OFF</span>
            </div>
            <p className="text-sm text-gray-500 dark:text-gray-300">Inclusive of all taxes</p>
          </div>

          {/* Promotions */}
          <div>
            <h3 className="text-lg font-semibold text-gray-900 dark:text-white mb-3">Special Offers</h3>
            <div className="flex flex-wrap gap-4">
              {hardcodedPromotions.map((promo, index) => (
                <div key={index} className="p-4 bg-green-50 dark:bg-green-800 border border-green-700 text-sm rounded-md w-full sm:w-64">
                  <div className="font-semibold text-green-900 dark:text-green-100 mb-1">{promo.promoType}</div>
                  <p className="text-green-800 dark:text-green-200">{promo.description}</p>
                  <div className="mt-2 text-xs font-mono px-3 py-1 rounded bg-green-100 dark:bg-green-700 text-green-900 dark:text-green-100 w-fit">
                    {promo.promoCode}
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Size Selection */}
          <div>
            <div className="flex items-center gap-2 mb-2">
              <h3 className="text-sm font-medium text-gray-900 dark:text-white">Size: {selectedSize || 'None'}</h3>
              <button onClick={() => setShowSizeChart(true)} className="text-xs text-blue-600 hover:underline">
                Size Chart
              </button>
            </div>
            <div className="flex flex-wrap gap-2">
              {allSizes.map((size) => {
                const available = availableSizes.includes(size);
                const isSelected = selectedSize === size;
                return (
                  <button
                    key={size}
                    onClick={() => available && handleSizeSelect(size)}
                    className={`px-4 py-2 rounded-md border text-sm font-semibold transition-colors duration-150 ${
                      isSelected
                        ? 'bg-[#a0522d] text-white border-[#a0522d]'
                        : available
                        ? 'bg-black text-white border-black hover:bg-[#a0522d] hover:border-[#a0522d]'
                        : 'opacity-50 cursor-not-allowed bg-gray-200 text-gray-400 border-gray-200'
                    }`}
                    disabled={!available}
                  >
                    {size}
                  </button>
                );
              })}
            </div>
          </div>

          {/* Quantity Selector */}
          <div className="flex items-center gap-4 mb-2">
            <span className="text-lg font-bold text-gray-900 dark:text-white">Quantity</span>
            <div className="flex items-center rounded-full bg-gray-200 dark:bg-gray-700 overflow-hidden h-10">
              <button
                onClick={() => setQuantity((q) => Math.max(1, q - 1))}
                className="w-10 h-10 flex items-center justify-center font-bold text-lg hover:bg-[#a0522d] hover:text-white"
              >
                <Minus />
              </button>
              <span className="w-12 text-center font-semibold">{quantity}</span>
              <button
                onClick={() => quantity < 5 && setQuantity(quantity + 1)}
                className={`w-10 h-10 flex items-center justify-center font-semibold hover:bg-[#a0522d] hover:text-white ${
                  quantity >= 5 ? 'opacity-50 cursor-not-allowed' : ''
                }`}
                disabled={quantity >= 5}
              >
                <Plus />
              </button>
            </div>
          </div>

          {/* Add to Cart */}
          <div className="flex gap-4 mt-6">
            <button
              onClick={handleAddToCart}
              className="w-full px-12 py-4 rounded-full font-bold text-lg bg-black text-white hover:bg-[#a0522d]"
              style={{ minWidth: 220 }}
            >
              Add to Cart - ₹{(discountedPrice * quantity).toLocaleString()}
            </button>
          </div>

          {/* Features */}
          <div className="pt-6 border-t">
            {features.map(({ icon: Icon, text }, i) => (
              <div key={i} className="flex items-center gap-2">
                <Icon className="text-green-600" />
                <span>{text}</span>
              </div>
            ))}
          </div>
        </motion.div>
      </div>

      {/* Size Chart Modal */}
      {showSizeChart && (
        <SizeChart isOpen={showSizeChart} onClose={() => setShowSizeChart(false)} category={product?.category} />
      )}
    </div>
  );
};

export default ProductDetail;
