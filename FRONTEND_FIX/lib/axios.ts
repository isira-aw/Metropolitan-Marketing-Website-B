/**
 * Production-Ready Axios Configuration
 *
 * KEY PRINCIPLES:
 * 1. Single axios instance for entire app
 * 2. NO redirects inside interceptors (causes loops)
 * 3. NO retry logic (causes repeated calls)
 * 4. Token read fresh from localStorage on every request
 * 5. Error handling delegates to components
 */

import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios';

// Create single axios instance
const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// ============================================================================
// REQUEST INTERCEPTOR
// ============================================================================
axiosInstance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    // Read token fresh from localStorage on EVERY request
    // This prevents race conditions and stale token issues
    const token = localStorage.getItem('token');

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    // Log for debugging (remove in production)
    if (process.env.NODE_ENV === 'development') {
      console.log(`[API] ${config.method?.toUpperCase()} ${config.url}`, {
        hasToken: !!token,
        headers: config.headers,
      });
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// ============================================================================
// RESPONSE INTERCEPTOR
// ============================================================================
axiosInstance.interceptors.response.use(
  // Success response - just return it
  (response) => {
    if (process.env.NODE_ENV === 'development') {
      console.log(`[API] SUCCESS ${response.config.url}`, response.data);
    }
    return response;
  },

  // Error response - handle carefully
  (error: AxiosError) => {
    const status = error.response?.status;
    const url = error.config?.url;

    if (process.env.NODE_ENV === 'development') {
      console.error(`[API] ERROR ${url} - Status: ${status}`, error.response?.data);
    }

    // ❌ DO NOT redirect here - causes infinite loops
    // ❌ DO NOT retry here - causes repeated calls
    // ❌ DO NOT modify localStorage here - causes state changes during render

    // Handle 401/403 by emitting a custom event instead
    if (status === 401 || status === 403) {
      // Only clear auth if it's a genuine auth failure (not just missing token)
      if (localStorage.getItem('token')) {
        console.warn('[API] Token expired or invalid - triggering logout');

        // Dispatch custom event for AuthContext to handle
        window.dispatchEvent(new CustomEvent('auth:unauthorized'));
      }
    }

    // Let the calling component handle the error
    return Promise.reject(error);
  }
);

export default axiosInstance;
