import React, { useState, useEffect } from 'react';
import { motion } from 'framer-motion';
import { Sun, Moon } from 'lucide-react';

const DarkModeToggle = () => {
  const [isDark, setIsDark] = useState(false);

  // Initialize theme on component mount
  useEffect(() => {
    // Check for saved theme preference or default to light mode
    const savedTheme = localStorage.getItem('stylesphere-theme');
    const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;
    
    const shouldBeDark = savedTheme === 'dark' || (!savedTheme && prefersDark);
    
    setIsDark(shouldBeDark);
    updateTheme(shouldBeDark);
  }, []);

  const updateTheme = (dark) => {
    const html = document.documentElement;
    
    if (dark) {
      html.classList.add('dark');
      html.style.colorScheme = 'dark';
    } else {
      html.classList.remove('dark');
      html.style.colorScheme = 'light';
    }
  };

  const toggleTheme = () => {
    const newTheme = !isDark;
    setIsDark(newTheme);
    updateTheme(newTheme);
    localStorage.setItem('stylesphere-theme', newTheme ? 'dark' : 'light');
  };

  return (
    <button
      onClick={toggleTheme}
      className={`
        relative flex items-center justify-center w-12 h-6 rounded-full p-0.5 
        transition-colors duration-200 focus:outline-none focus:ring-2 
        focus:ring-blue-500 focus:ring-offset-2
        ${isDark 
          ? 'bg-blue-600 hover:bg-blue-700' 
          : 'bg-gray-200 hover:bg-gray-300'
        }
      `}
      aria-label={`Switch to ${isDark ? 'light' : 'dark'} mode`}
    >
      <motion.div
        className={`
          absolute w-5 h-5 rounded-full shadow-md flex items-center justify-center
          ${isDark ? 'bg-gray-800' : 'bg-white'}
        `}
        animate={{
          x: isDark ? 24 : 2,
        }}
        transition={{
          type: "spring",
          stiffness: 700,
          damping: 30
        }}
      >
        {isDark ? (
          <Moon className="w-3 h-3 text-blue-400" />
        ) : (
          <Sun className="w-3 h-3 text-yellow-500" />
        )}
      </motion.div>
    </button>
  );
};

export default DarkModeToggle;
