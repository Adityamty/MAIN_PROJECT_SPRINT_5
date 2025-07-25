import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { Home } from 'lucide-react';

const BreadCrumbHeader = ({ productName = '' }) => {
  const location = useLocation();
  const pathParts = location.pathname.split('/').filter(Boolean); // removes empty strings

  const renderBreadcrumb = () => {
    const crumbs = [];

    // Home icon
    crumbs.push(
      <Link
        key="home"
        to="/"
        className="text-gray-700 dark:text-white hover:text-[#8B4513] transition"
      >
        <Home />
      </Link>
    );

    if (pathParts.length > 0) {
      crumbs.push(
        <span key="sep0" className="mx-2 text-gray-500 dark:text-gray-300">
          /
        </span>
      );
    }

    // Build remaining breadcrumb
    pathParts.forEach((part, index) => {
      const isLast = index === pathParts.length - 1;
      let label = part;

      if (part === 'products') label = 'Products';
      else if (part === 'cart') label = 'Cart';
      else if (productName && isLast) label = productName;

      if (!isLast) {
        const pathTo = '/' + pathParts.slice(0, index + 1).join('/');
        crumbs.push(
          <React.Fragment key={part}>
            <Link
              to={pathTo}
              className="text-gray-700 dark:text-white hover:text-[#8B4513] transition"
            >
              {label}
            </Link>
            <span className="mx-2 text-gray-500 dark:text-gray-300">/</span>
          </React.Fragment>
        );
      } else {
        crumbs.push(
          <span
            key={part}
            className="text-gray-900 dark:text-white font-semibold"
          >
            {label}
          </span>
        );
      }
    });

    return crumbs;
  };

  return (
    <div className="bg-white dark:bg-gray-800 shadow-sm sticky top-0 z-30 transition-colors duration-300">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center gap-2 flex-wrap">
        {renderBreadcrumb()}
      </div>
    </div>
  );
};

export default BreadCrumbHeader;
