import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.jsx';
import './index.css';

// Initialize performance monitoring in development
if (process.env.NODE_ENV === 'development') {
  if (typeof window !== 'undefined') {
    window.__REACT_DEVTOOLS_GLOBAL_HOOK__?.onCommitFiberRoot || (window.__REACT_DEVTOOLS_GLOBAL_HOOK__ = {});
  }
}

// Error handling for the root render
function renderApp() {
  const rootElement = document.getElementById('root');

  if (!rootElement) {
    throw new Error('Root element not found. Make sure you have a div with id="root" in your HTML.');
  }

  const root = createRoot(rootElement);

  root.render(
    <StrictMode>
      <App />
    </StrictMode>
  );
}

// Initialize the application
try {
  renderApp();
} catch (error) {
  console.error('Failed to render the application:', error);

  const rootElement = document.getElementById('root');
  if (rootElement) {
    rootElement.innerHTML = `
      <div style="
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        background-color: #f9fafb;
        font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
      ">
        <div style="text-align: center; padding: 2rem; max-width: 400px;">
          <h1 style="color: #ef4444; margin-bottom: 1rem;">Application Error</h1>
          <p style="color: #6b7280; margin-bottom: 1rem;">
            Failed to load Fashinhub. Please refresh the page or contact support.
          </p>
          <button 
            onclick="window.location.reload()" 
            style="
              background-color: #3b82f6;
              color: white;
              padding: 0.5rem 1rem;
              border: none;
              border-radius: 0.5rem;
              cursor: pointer;
              font-weight: 500;
            "
          >
            Refresh Page
          </button>
        </div>
      </div>
    `;
  }
}

// Handle global unhandled errors
window.addEventListener('error', (event) => {
  console.error('Global error:', event.error);

  if (process.env.NODE_ENV === 'production' && window.gtag) {
    window.gtag('event', 'exception', {
      description: event.error?.message || 'Unknown error',
      fatal: true
    });
  }
});

window.addEventListener('unhandledrejection', (event) => {
  console.error('Unhandled promise rejection:', event.reason);
  event.preventDefault();

  if (process.env.NODE_ENV === 'production' && window.gtag) {
    window.gtag('event', 'exception', {
      description: event.reason?.message || 'Unhandled promise rejection',
      fatal: false
    });
  }
});

// Service Worker registration for PWA (optional)
if ('serviceWorker' in navigator && import.meta.env.PROD) {
  window.addEventListener('load', () => {
    navigator.serviceWorker.register('/sw.js')
      .then((registration) => {
        console.log('SW registered: ', registration);
      })
      .catch((registrationError) => {
        console.log('SW registration failed: ', registrationError);
      });
  });
}

// Development helpers
if (import.meta.env.DEV) {
  console.log('🚀 Fashionhub E-commerce App starting in development mode');

  const requiredEnvVars = ['VITE_API_URL'];

  const missingEnvVars = requiredEnvVars.filter(
    envVar => !import.meta.env[envVar]
  );

  if (missingEnvVars.length > 0) {
    console.warn('⚠️ Missing environment variables:', missingEnvVars.join(', '));
  } else {
    console.log('✅ All required environment variables are set');
  }

  if (window.performance && window.performance.mark) {
    window.performance.mark('app-start');

    window.addEventListener('load', () => {
      window.performance.mark('app-loaded');
      window.performance.measure('app-load-time', 'app-start', 'app-loaded');

      const measure = window.performance.getEntriesByName('app-load-time')[0];
      console.log(`📊 App load time: ${measure.duration.toFixed(2)}ms`);
    });
  }
}
