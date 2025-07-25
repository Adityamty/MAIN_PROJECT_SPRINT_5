import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { categoryService } from '../../services/snb/categoryService';
import LoadingSpinner from './ui/LoadingSpinner';

const CategoryGrid = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        setLoading(true);
        const data = await categoryService.getCategories();
        setCategories(data || []);
      } catch (err) {
        console.error('Failed to load categories:', err);
        setError('Failed to load categories');
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, []);

  const handleCategoryClick = (categoryName) => {
    navigate(`/products?category=${encodeURIComponent(categoryName)}`);
  };

  if (loading) {
    return (
      <section className="py-10 font-poppins">
        <div className="max-w-7xl mx-auto px-4 text-center">
          <LoadingSpinner size="lg" />
          <p className="mt-4 text-gray-600">Loading categories...</p>
        </div>
      </section>
    );
  }

  if (error) {
    return (
      <section className="py-10 font-poppins">
        <div className="max-w-7xl mx-auto px-4 text-center">
          <p className="text-red-500">{error}</p>
        </div>
      </section>
    );
  }

  return (
    <section className="py-10 font-poppins">
      <div className="max-w-7xl mx-auto px-4">
        <div className="flex justify-between items-center mb-8">
          <h2 className="text-2xl font-semibold text-gray-800">Shop By Category</h2>
          <button
            onClick={() => navigate('/products')}
            className="bg-[#a0522d] hover:bg-[#8b4513] text-white px-6 py-2 rounded-lg font-medium transition-colors duration-200 flex items-center gap-2"
          >
            <span>Browse All Products</span>
            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 7l5 5m0 0l-5 5m5-5H6" />
            </svg>
          </button>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          {categories.map((category) => (
            <div
              key={category.id}
              className="bg-white rounded-xl shadow hover:shadow-md overflow-hidden text-center cursor-pointer transform hover:scale-105 transition-transform duration-200"
              onClick={() => handleCategoryClick(category.categoryName)}
            >
              <img
                src={category.categoryImage}
                alt={category.categoryName}
                className="w-full h-72 object-cover rounded-t-xl"
                onError={(e) => {
                  e.target.src = '/placeholder-category.jpg';
                }}
              />
              <div className="p-4">
                <h4 className="text-lg font-semibold">{category.categoryName}</h4>
                <p className="text-sm text-gray-600 mt-2">{category.description}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};

export default CategoryGrid;
