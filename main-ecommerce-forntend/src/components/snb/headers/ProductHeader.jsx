import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import DarkModeToggle from '../ui/DarkModeToggle';
import Cart from '../../../assets/snb/images/WhiteCart.svg';

const ProductHeader = () => {
  const [userName, setUserName] = useState('');
  const navigate = useNavigate();
 
  useEffect(() => {
    const storedUser = localStorage.getItem('userName');
    if (storedUser) setUserName(storedUser);
  }, []);
 
  const handleLogout = () => {
    localStorage.removeItem('userName');
    setUserName('');
    navigate('/login');
  };
 
  const goToHome = () => {
    navigate('/');
  };
 
  return (
    <div className="bg-[#2c2c2c] text-gray-100 hover:text-rose-500 py-4 shadow">
      <div className="px-5 mx-auto flex flex-col md:flex-row items-center justify-between gap-5">
 
        {/* Clickable Logo */}
        <div
          className="text-3xl font-bold !text-white ms-4 mb-2 cursor-pointer"
          onClick={goToHome}
        >
          FashionHub
        </div>
 
        {/* Right section - Links + User Info */}
        <div className="flex items-center gap-4">
          <a href="/products" className="text-lg font-semibold !text-white me-5">
            Browse
          </a>
          <a href="#" className="text-gray-700 hover:text-blue-500">
            <img src={Cart} alt="Cart" width="25" height="25" />
          </a>
 
          <DarkModeToggle />
 
          {userName ? (
            <div className="flex items-center gap-3">
              <span className="text-white text-lg font-medium">Hi, {userName}</span>
              <button
                onClick={handleLogout}
                className="text-sm text-rose-400 hover:text-white underline"
              >
                Logout
              </button>
            </div>
          ) : (
            <a href="/login" className="text-lg font-semibold !text-white me-5">
              Login
            </a>
          )}
        </div>
      </div>
    </div>
  );
};
 
export default ProductHeader;
 
 