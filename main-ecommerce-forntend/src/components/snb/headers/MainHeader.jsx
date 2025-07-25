import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import DarkModeToggle from '../ui/DarkModeToggle';
import Cart from '../../../assets/snb/images/WhiteCart.svg';

const MainHeader = () => {
  const [searchText, setSearchText] = useState('');
  const [userName, setUserName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = sessionStorage.getItem('userName');
    if (storedUser) setUserName(storedUser);
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchText.trim()) {
      navigate(`/products?query=${encodeURIComponent(searchText.trim())}`);
      
    }
  };

  const handleLogout = () => {
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('userName');
    sessionStorage.removeItem('userEmail');
    setUserName('');
    navigate('/login');
  };

  return (
    <div className="bg-[#2c2c2c] text-gray-100 hover:text-rose-500 py-4 shadow">
      <div className="px-5 mx-auto flex flex-col md:flex-row items-center justify-between gap-5">
        {/* Logo */}
        <a href="/">
          <div className="text-3xl font-bold !text-white ms-4 mb-2">
            FashionHub
          </div>
        </a>

        {/* Search Bar */}
        <form onSubmit={handleSearch} className="w-full md:w-1/2">
          <input
            type="text"
            value={searchText}
            onChange={(e) => setSearchText(e.target.value)}
            placeholder="I'm looking for..."
            className="w-full px-4 py-2 text-base border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </form>

        {/* Icons + Dark Toggle + User Info */}
        <div className="flex items-center gap-4">
          <button className="text-lg font-semibold !text-white me-5" onClick={() => navigate('/products')}>
            Browse
          </button>

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

export default MainHeader;
