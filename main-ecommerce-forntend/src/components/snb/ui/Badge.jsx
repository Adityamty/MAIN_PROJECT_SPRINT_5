import React from 'react';
import { clsx } from 'clsx';

const Badge = ({ children, variant = 'default', size = 'md', className, ...props }) => {
  const baseStyles = `
    inline-flex items-center rounded-full font-semibold transition-colors 
    focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2
  `;

  const variants = {
    default: 'badge badge-primary',
    secondary: 'badge badge-secondary',
    success: 'badge badge-success',
    warning: 'badge badge-warning',
    destructive: 'badge badge-error',
    primary: 'bg-[#a0522d] text-white hover:bg-[#8b4513]',
    outline: 'border border-secondary text-secondary hover:bg-tertiary',
    category: 'bg-purple-100 text-purple-800 dark:bg-purple-900 dark:text-purple-200',
  };

  const sizes = {
    sm: 'px-2 py-0.5 text-xs',
    md: 'px-2.5 py-1 text-sm',
    lg: 'px-3 py-1.5 text-base',
  };

  return (
    <span
      className={clsx(baseStyles, variants[variant], sizes[size], className)}
      {...props}
    >
      {children}
    </span>
  );
};

export default Badge;
