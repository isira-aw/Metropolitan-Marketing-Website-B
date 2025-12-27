/**
 * Centralized API Service Layer
 *
 * KEY PRINCIPLES:
 * 1. All API calls go through this file
 * 2. NO raw axios imports anywhere else
 * 3. Type-safe API calls
 * 4. Single source of truth for endpoints
 */

import axiosInstance from './axios';
import { AxiosResponse } from 'axios';

// ============================================================================
// TYPES
// ============================================================================
export interface Admin {
  id: number;
  username: string;
  email: string;
  license: boolean;
  password?: string; // Only for create/update
}

export interface Category {
  id: number;
  name: string;
  description?: string;
}

export interface Product {
  id: number;
  name: string;
  description?: string;
  categoryId: number;
  price?: number;
  imageUrl?: string;
}

export interface Employee {
  id: number;
  name: string;
  position: string;
  department?: string;
  email?: string;
}

// ============================================================================
// AUTH API
// ============================================================================
export const authApi = {
  login: (username: string, password: string) =>
    axiosInstance.post<{ token: string; admin: Admin }>('/api/auth/login', {
      username,
      password,
    }),
};

// ============================================================================
// ADMIN PROFILE API
// ============================================================================
export const profileApi = {
  getProfile: () => axiosInstance.get<Admin>('/api/admin/profile'),

  updateProfile: (data: {
    username?: string;
    email?: string;
    password?: string;
    license?: boolean;
  }) => axiosInstance.put<Admin>('/api/admin/profile', data),
};

// ============================================================================
// ADMIN MANAGEMENT API
// ============================================================================
export const adminApi = {
  getAll: () => axiosInstance.get<Admin[]>('/api/admin/admins'),

  create: (data: { username: string; email: string; password: string; license?: boolean }) =>
    axiosInstance.post<Admin>('/api/admin/admins', data),

  update: (
    id: number,
    data: { username?: string; email?: string; password?: string; license?: boolean }
  ) => axiosInstance.put<Admin>(`/api/admin/admins/${id}`, data),

  delete: (id: number) => axiosInstance.delete(`/api/admin/admins/${id}`),
};

// ============================================================================
// CATEGORIES API
// ============================================================================
export const categoryApi = {
  getAll: () => axiosInstance.get<Category[]>('/api/admin/categories'),

  create: (data: { name: string; description?: string }) =>
    axiosInstance.post<Category>('/api/admin/categories', data),

  update: (id: number, data: { name?: string; description?: string }) =>
    axiosInstance.put<Category>(`/api/admin/categories/${id}`, data),

  delete: (id: number) => axiosInstance.delete(`/api/admin/categories/${id}`),
};

// ============================================================================
// PRODUCTS API
// ============================================================================
export const productApi = {
  getAll: () => axiosInstance.get<Product[]>('/api/admin/products'),

  getById: (id: number) => axiosInstance.get<Product>(`/api/admin/products/${id}`),

  create: (data: {
    name: string;
    description?: string;
    categoryId: number;
    price?: number;
    imageUrl?: string;
  }) => axiosInstance.post<Product>('/api/admin/products', data),

  update: (
    id: number,
    data: {
      name?: string;
      description?: string;
      categoryId?: number;
      price?: number;
      imageUrl?: string;
    }
  ) => axiosInstance.put<Product>(`/api/admin/products/${id}`, data),

  delete: (id: number) => axiosInstance.delete(`/api/admin/products/${id}`),
};

// ============================================================================
// EMPLOYEES API (if you have this endpoint)
// ============================================================================
export const employeeApi = {
  getAll: () => axiosInstance.get<Employee[]>('/api/admin/employees'),

  getById: (id: number) => axiosInstance.get<Employee>(`/api/admin/employees/${id}`),

  create: (data: { name: string; position: string; department?: string; email?: string }) =>
    axiosInstance.post<Employee>('/api/admin/employees', data),

  update: (
    id: number,
    data: { name?: string; position?: string; department?: string; email?: string }
  ) => axiosInstance.put<Employee>(`/api/admin/employees/${id}`, data),

  delete: (id: number) => axiosInstance.delete(`/api/admin/employees/${id}`),
};

// ============================================================================
// FILE UPLOAD API
// ============================================================================
export const fileApi = {
  upload: (file: File) => {
    const formData = new FormData();
    formData.append('file', file);

    return axiosInstance.post<{ url: string }>('/api/admin/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  },

  delete: (filename: string) => axiosInstance.delete(`/api/admin/files/${filename}`),
};

// ============================================================================
// EXPORT ALL
// ============================================================================
export default {
  auth: authApi,
  profile: profileApi,
  admin: adminApi,
  category: categoryApi,
  product: productApi,
  employee: employeeApi,
  file: fileApi,
};
