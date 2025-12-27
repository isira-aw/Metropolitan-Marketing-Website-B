/**
 * Example: Employees Page (Production-Ready Pattern)
 *
 * KEY PRINCIPLES:
 * 1. NO dependencies on auth state in useEffect (prevents loops)
 * 2. Fetch data ONCE on mount
 * 3. Handle errors gracefully
 * 4. Redirect only AFTER user action fails
 * 5. Use loading/error states properly
 */

'use client';

import { useState, useEffect, useRef } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/contexts/AuthContext';
import { employeeApi, Employee } from '@/lib/api';
import { AxiosError } from 'axios';

export default function EmployeesPage() {
  const router = useRouter();
  const { isAuthenticated, isLoading: authLoading, logout } = useAuth();

  // ============================================================================
  // STATE
  // ============================================================================
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Prevent double fetch in React Strict Mode
  const hasFetched = useRef(false);

  // ============================================================================
  // AUTH CHECK (separate from data fetching)
  // ============================================================================
  useEffect(() => {
    // Wait for auth to initialize
    if (authLoading) return;

    // If not authenticated, redirect to login
    if (!isAuthenticated) {
      console.log('[Employees] Not authenticated, redirecting to login');
      router.push('/admin/login');
    }
  }, [isAuthenticated, authLoading, router]);

  // ============================================================================
  // FETCH EMPLOYEES (ONCE on mount, independent of auth state)
  // ============================================================================
  useEffect(() => {
    // ❌ WRONG: if (!isAuthenticated) return; // This causes loops!
    // ❌ WRONG: }, [isAuthenticated]); // This causes repeated calls!

    // ✅ CORRECT: Fetch once, let axios handle auth errors
    if (hasFetched.current) return;

    const fetchEmployees = async () => {
      try {
        setIsLoading(true);
        setError(null);

        console.log('[Employees] Fetching employees...');
        const response = await employeeApi.getAll();

        setEmployees(response.data);
        console.log('[Employees] Loaded', response.data.length, 'employees');

        hasFetched.current = true; // Mark as fetched
      } catch (err) {
        const axiosError = err as AxiosError;
        console.error('[Employees] Failed to fetch:', axiosError.response?.status);

        // Handle specific error cases
        if (axiosError.response?.status === 401 || axiosError.response?.status === 403) {
          // Token is invalid - logout will happen via axios event listener
          setError('Your session has expired. Please log in again.');

          // Redirect after a delay to show the message
          setTimeout(() => {
            logout();
            router.push('/admin/login');
          }, 2000);
        } else {
          setError('Failed to load employees. Please try again.');
        }
      } finally {
        setIsLoading(false);
      }
    };

    // Only fetch if we think we're authenticated
    // (This prevents unnecessary API calls on the login page)
    if (!authLoading && isAuthenticated) {
      fetchEmployees();
    }
  }, []); // ✅ Empty deps - fetch ONCE only

  // ============================================================================
  // HANDLERS
  // ============================================================================
  const handleRefresh = async () => {
    hasFetched.current = false; // Allow re-fetch
    setIsLoading(true);
    setError(null);

    try {
      const response = await employeeApi.getAll();
      setEmployees(response.data);
    } catch (err) {
      const axiosError = err as AxiosError;
      if (axiosError.response?.status === 401 || axiosError.response?.status === 403) {
        logout();
        router.push('/admin/login');
      } else {
        setError('Failed to refresh employees.');
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this employee?')) return;

    try {
      await employeeApi.delete(id);
      // Remove from local state
      setEmployees((prev) => prev.filter((emp) => emp.id !== id));
    } catch (err) {
      const axiosError = err as AxiosError;
      if (axiosError.response?.status === 401 || axiosError.response?.status === 403) {
        logout();
        router.push('/admin/login');
      } else {
        alert('Failed to delete employee.');
      }
    }
  };

  // ============================================================================
  // RENDER
  // ============================================================================

  // Show nothing while auth is initializing
  if (authLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-gray-500">Initializing...</div>
      </div>
    );
  }

  // Show nothing if not authenticated (will redirect)
  if (!isAuthenticated) {
    return null;
  }

  // Show loading state
  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-gray-500">Loading employees...</div>
      </div>
    );
  }

  // Show error state
  if (error) {
    return (
      <div className="flex flex-col items-center justify-center min-h-screen gap-4">
        <div className="text-red-500">{error}</div>
        <button
          onClick={handleRefresh}
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Retry
        </button>
      </div>
    );
  }

  // Show data
  return (
    <div className="container mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">Employees</h1>
        <button
          onClick={handleRefresh}
          className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
        >
          Refresh
        </button>
      </div>

      {employees.length === 0 ? (
        <div className="text-gray-500 text-center py-12">No employees found</div>
      ) : (
        <div className="grid gap-4">
          {employees.map((employee) => (
            <div
              key={employee.id}
              className="border rounded-lg p-4 flex justify-between items-center"
            >
              <div>
                <h3 className="font-semibold">{employee.name}</h3>
                <p className="text-sm text-gray-600">{employee.position}</p>
                {employee.department && (
                  <p className="text-sm text-gray-500">{employee.department}</p>
                )}
              </div>
              <button
                onClick={() => handleDelete(employee.id)}
                className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600"
              >
                Delete
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
