/**
 * Production-Ready AuthContext
 *
 * KEY PRINCIPLES:
 * 1. Single source of truth (localStorage)
 * 2. NO side effects in getters
 * 3. NO automatic redirects (components decide)
 * 4. NO token refresh logic (keep it simple)
 * 5. Listen for unauthorized events from axios
 */

'use client';

import {
  createContext,
  useContext,
  useState,
  useEffect,
  useCallback,
  ReactNode,
  useRef,
} from 'react';
import axiosInstance from '@/lib/axios';

// ============================================================================
// TYPES
// ============================================================================
interface Admin {
  id: number;
  username: string;
  email: string;
  license: boolean;
}

interface AuthContextType {
  admin: Admin | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  updateAdmin: (admin: Admin) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// ============================================================================
// PROVIDER
// ============================================================================
export function AuthProvider({ children }: { children: ReactNode }) {
  const [admin, setAdmin] = useState<Admin | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const isInitialized = useRef(false);

  // ============================================================================
  // INITIALIZE FROM LOCALSTORAGE (ONCE ON MOUNT)
  // ============================================================================
  useEffect(() => {
    // Prevent double initialization in React Strict Mode
    if (isInitialized.current) return;
    isInitialized.current = true;

    const initAuth = () => {
      try {
        const storedToken = localStorage.getItem('token');
        const storedAdmin = localStorage.getItem('admin');

        if (storedToken && storedAdmin) {
          setAdmin(JSON.parse(storedAdmin));
          console.log('[Auth] Restored session from localStorage');
        }
      } catch (error) {
        console.error('[Auth] Failed to restore session:', error);
        // Clear corrupted data
        localStorage.removeItem('token');
        localStorage.removeItem('admin');
      } finally {
        setIsLoading(false);
      }
    };

    initAuth();
  }, []); // Empty deps - run ONCE

  // ============================================================================
  // LISTEN FOR UNAUTHORIZED EVENTS FROM AXIOS
  // ============================================================================
  useEffect(() => {
    const handleUnauthorized = () => {
      console.log('[Auth] Unauthorized event received - logging out');
      logout();
    };

    window.addEventListener('auth:unauthorized', handleUnauthorized);

    return () => {
      window.removeEventListener('auth:unauthorized', handleUnauthorized);
    };
  }, []); // Empty deps - setup ONCE

  // ============================================================================
  // LOGIN
  // ============================================================================
  const login = useCallback(async (username: string, password: string) => {
    try {
      const response = await axiosInstance.post('/api/auth/login', {
        username,
        password,
      });

      const { token, admin: adminData } = response.data;

      // Store in localStorage
      localStorage.setItem('token', token);
      localStorage.setItem('admin', JSON.stringify(adminData));

      // Update state
      setAdmin(adminData);

      console.log('[Auth] Login successful:', adminData.username);
    } catch (error: any) {
      console.error('[Auth] Login failed:', error.response?.data || error.message);
      throw error; // Let component handle the error
    }
  }, []);

  // ============================================================================
  // LOGOUT
  // ============================================================================
  const logout = useCallback(() => {
    console.log('[Auth] Logging out');

    // Clear localStorage
    localStorage.removeItem('token');
    localStorage.removeItem('admin');

    // Clear state
    setAdmin(null);

    // Note: We don't redirect here - let the component decide
  }, []);

  // ============================================================================
  // UPDATE ADMIN (after profile edit)
  // ============================================================================
  const updateAdmin = useCallback((updatedAdmin: Admin) => {
    setAdmin(updatedAdmin);
    localStorage.setItem('admin', JSON.stringify(updatedAdmin));
  }, []);

  // ============================================================================
  // CONTEXT VALUE
  // ============================================================================
  const value: AuthContextType = {
    admin,
    isAuthenticated: !!admin,
    isLoading,
    login,
    logout,
    updateAdmin,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

// ============================================================================
// HOOK
// ============================================================================
export function useAuth() {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}
