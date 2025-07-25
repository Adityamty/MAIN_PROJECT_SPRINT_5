import React from 'react';
import { motion } from 'framer-motion';
import { clsx } from 'clsx';

const Button = React.forwardRef(({ 
  className, 
  variant = 'primary', 
  size = 'md', 
  children, 
  loading = false,
  disabled = false,
  ...props 
}, ref) => {
  const baseStyles = `
    inline-flex items-center justify-center whitespace-nowrap rounded-md text-sm font-medium 
    transition-all duration-200 focus-visible:outline-none focus-visible:ring-2 
    focus-visible:ring-blue-500 focus-visible:ring-offset-2 disabled:pointer-events-none 
    disabled:opacity-50 active:scale-95
  `;

  const variants = {
    primary: `
      bg-[#a0522d] text-white hover:bg-[#8b4513] shadow-lg hover:shadow-xl
      focus-visible:ring-blue-500
    `,
    secondary: `
      bg-tertiary text-secondary hover:bg-gray-200 border border-secondary
      dark:hover:bg-gray-600 focus-visible:ring-gray-500
    `,
    outline: `
      border border-secondary bg-transparent hover:bg-tertiary text-secondary
      focus-visible:ring-gray-500
    `,
    ghost: `
      text-secondary hover:bg-tertiary focus-visible:ring-gray-500
    `,
    destructive: `
      bg-red-600 text-white hover:bg-red-700 shadow-lg
      focus-visible:ring-red-500
    `,
  };

  const sizes = {
    sm: 'h-9 px-3 text-xs',
    md: 'h-10 px-4 py-2',
    lg: 'h-11 px-8 text-base',
    xl: 'h-12 px-10 text-lg',
  };

  return (
    <motion.button
      className={clsx(baseStyles, variants[variant], sizes[size], className)}
      ref={ref}
      disabled={disabled || loading}
      whileHover={{ scale: disabled ? 1 : 1.02 }}
      whileTap={{ scale: disabled ? 1 : 0.98 }}
      {...props}
    >
      {loading && (
        <motion.div
          className="mr-2 h-4 w-4 animate-spin rounded-full border-2 border-current border-t-transparent"
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
        />
      )}
      {children}
    </motion.button>
  );
});

Button.displayName = 'Button';

export default Button;
