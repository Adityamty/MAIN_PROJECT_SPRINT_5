import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import LoadingSpinner from './ui/LoadingSpinner';
import RoleBasedErrorBoundary from './RoleBasedErrorBoundary';

const ProtectedRoute = ({ children, requireAuth = true }) => {
  const { isAuthenticated, loading, user } = useAuth();

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-secondary">
        <div className="text-center">
          <LoadingSpinner size="xl" className="mx-auto mb-4" />
          <p className="text-secondary font-medium">Loading...</p>
        </div>
      </div>
    );
  }

  if (requireAuth && !isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!requireAuth && isAuthenticated) {
    return <Navigate to="/" replace />;
  }

  // Check if user has customer role for protected routes
  if (requireAuth && isAuthenticated) {
    const hasCustomerRole = user?.roles?.some(role => 
      role === 'customer' || 
      role === 'ROLE_customer' ||
      role.toLowerCase() === 'customer'
    );

    return (
      <RoleBasedErrorBoundary hasCustomerRole={hasCustomerRole} loading={false}>
        {children}
      </RoleBasedErrorBoundary>
    );
  }

  return children;
};

export default ProtectedRoute;
