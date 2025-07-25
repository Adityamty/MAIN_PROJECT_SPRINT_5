import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import federation from '@originjs/vite-plugin-federation'

export default defineConfig({
  plugins: [
    react(),
    federation({
      name: 'userFrontend',
      filename: 'remoteEntry.js',
      exposes: {
        './App': './src/App.jsx'  // Adjust if file path differs
      },
      shared: ['react', 'react-dom', 'react-router-dom']
    })
  ],
  server: {
    port: 3000
  },
  build: {
    target: 'esnext',
    minify: false,
    cssCodeSplit: false
  }
})