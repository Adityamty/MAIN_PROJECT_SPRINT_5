// Test utility to verify role-based authentication
import authService from '../services/authService';

export const testRoleAuth = () => {
  console.log('Testing role-based authentication...');
  
  // Test JWT decoding
  const mockToken = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsInJvbGVzIjpbImN1c3RvbWVyIl0sImlhdCI6MTYwMDAwMDAwMCwiZXhwIjoxNjAwMDAwMDAwfQ.signature';
  
  try {
    const decoded = authService.decodeJWT(mockToken);
    console.log('Decoded token:', decoded);
    
    const hasCustomer = authService.hasCustomerRole(decoded);
    console.log('Has customer role:', hasCustomer);
    
    return { success: true, decoded, hasCustomer };
  } catch (error) {
    console.error('Error testing role auth:', error);
    return { success: false, error: error.message };
  }
};

// Test with different role scenarios
export const testRoleScenarios = () => {
  console.log('Testing different role scenarios...');
  
  const scenarios = [
    { name: 'Customer role', roles: ['customer'] },
    { name: 'ROLE_customer', roles: ['ROLE_customer'] },
    { name: 'Customer with ID 1', roles: ['customer'], id: 1 },
    { name: 'Admin role (should fail)', roles: ['admin'] },
    { name: 'No roles (should fail)', roles: [] },
  ];
  
  scenarios.forEach(scenario => {
    console.log(`\n--- Testing: ${scenario.name} ---`);
    const mockTokenData = {
      sub: 'testuser',
      roles: scenario.roles,
      id: scenario.id || 2,
      iat: Math.floor(Date.now() / 1000),
      exp: Math.floor(Date.now() / 1000) + 3600
    };
    
    const hasCustomer = authService.hasCustomerRole(mockTokenData);
    console.log(`Roles: ${JSON.stringify(scenario.roles)}, Has customer access: ${hasCustomer}`);
  });
};
