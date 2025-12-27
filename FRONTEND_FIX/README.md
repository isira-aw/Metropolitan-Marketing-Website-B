# 🎯 Production-Ready Authentication Flow - Complete Fix

## 📊 REQUEST FLOW DIAGRAM

```
┌─────────────────────────────────────────────────────────────────────────┐
│ 1. USER ACTION (Component)                                              │
│    - User clicks "Load Employees"                                        │
│    - Component calls: employeeApi.getAll()                              │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 2. API SERVICE LAYER (lib/api.ts)                                       │
│    - employeeApi.getAll() → axiosInstance.get('/api/admin/employees')   │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 3. AXIOS REQUEST INTERCEPTOR (lib/axios.ts)                             │
│    - Reads token from localStorage: const token = localStorage.getItem  │
│    - Adds header: Authorization: Bearer {token}                          │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 4. BROWSER (Network Layer)                                              │
│                                                                           │
│    Step 4a: OPTIONS Preflight Request                                   │
│    ────────────────────────────────────────────────                     │
│    OPTIONS http://localhost:8080/api/admin/employees                    │
│    Origin: http://localhost:3001                                         │
│    Access-Control-Request-Method: GET                                    │
│    Access-Control-Request-Headers: authorization,content-type           │
│                                                                           │
│    Step 4b: Spring Boot CORS Response                                   │
│    ──────────────────────────────────────                               │
│    200 OK                                                                │
│    Access-Control-Allow-Origin: http://localhost:3001                   │
│    Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS        │
│    Access-Control-Allow-Headers: *                                       │
│    Access-Control-Allow-Credentials: true                                │
│                                                                           │
│    Step 4c: Actual GET Request                                           │
│    ─────────────────────────────                                        │
│    GET http://localhost:8080/api/admin/employees                        │
│    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...                         │
│    Content-Type: application/json                                        │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 5. SPRING BOOT BACKEND                                                   │
│                                                                           │
│    Step 5a: JwtAuthenticationFilter (line 28)                           │
│    ────────────────────────────────────────────                         │
│    - Extracts: Authorization: Bearer {token}                             │
│    - Validates JWT signature and expiration                              │
│    - Loads UserDetails from database                                     │
│    - Sets SecurityContext authentication                                 │
│                                                                           │
│    Step 5b: Security Filter Chain (SecurityConfig line 42)              │
│    ───────────────────────────────────────────────────────              │
│    - Checks: /api/admin/** requires authenticated()                      │
│    - If authenticated → proceed to controller                            │
│    - If NOT authenticated → return 403 Forbidden                         │
│                                                                           │
│    Step 5c: Controller (if authenticated)                                │
│    ─────────────────────────────────────────                            │
│    - Fetches employees from database                                     │
│    - Returns: 200 OK with JSON data                                      │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 6. AXIOS RESPONSE INTERCEPTOR (lib/axios.ts)                            │
│                                                                           │
│    SUCCESS (200-299):                                                    │
│    - Returns response.data to caller                                     │
│    - Component receives: Employee[]                                      │
│                                                                           │
│    ERROR (401/403):                                                      │
│    - Logs error for debugging                                            │
│    - Dispatches: window.dispatchEvent('auth:unauthorized')               │
│    - Returns Promise.reject(error) to caller                             │
└────────────────────────┬────────────────────────────────────────────────┘
                         │
                         ▼
┌─────────────────────────────────────────────────────────────────────────┐
│ 7. ERROR HANDLING                                                        │
│                                                                           │
│    AuthContext (listens for 'auth:unauthorized' event):                 │
│    - Calls logout()                                                      │
│    - Clears localStorage                                                 │
│    - Sets admin = null                                                   │
│                                                                           │
│    Component (receives rejected promise):                                │
│    - Catches error in try/catch                                          │
│    - Shows error message to user                                         │
│    - Redirects to /admin/login                                           │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 🌐 EXPECTED BROWSER NETWORK TAB BEHAVIOR

### ✅ CORRECT (After Fix)

When you load `/admin/employees` page:

```
1. OPTIONS /api/admin/employees     [Preflight]
   Status: 200 OK
   Time: ~10ms

2. GET /api/admin/employees         [Actual Request]
   Status: 200 OK
   Request Headers:
     Authorization: Bearer eyJhbGci...
     Content-Type: application/json
   Response: [{"id": 1, "name": "John"}, ...]
   Time: ~50ms
```

**Total: 2 requests (1 OPTIONS + 1 GET)**

### ❌ BROKEN (Before Fix)

```
1. OPTIONS /api/admin/employees
2. GET /api/admin/employees         → 403 Forbidden
3. OPTIONS /api/admin/employees     [Retry caused by interceptor]
4. GET /api/admin/employees         → 403 Forbidden
5. OPTIONS /api/admin/employees     [Loop continues...]
6. GET /api/admin/employees         → 403 Forbidden
... repeats 10+ times
```

**Total: 20+ requests (infinite loop until timeout)**

---

## 🔄 WHY PREFLIGHTS HAPPEN (AND WHY IT'S NORMAL)

### CORS Preflight is Triggered By:

1. **Custom Headers** (like `Authorization: Bearer`)
2. **Methods** other than GET/POST/HEAD
3. **Content-Type** other than application/x-www-form-urlencoded, multipart/form-data, text/plain

### Your Backend Correctly Handles Preflights:

```java
// CorsConfig.java line 22
configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
configuration.setAllowedHeaders(Arrays.asList("*"));
configuration.setAllowCredentials(true);
```

**This is CORRECT. Do NOT try to "disable" preflights.**

### How to Minimize Preflights:

✅ **DO:**
- Accept that every protected request will have OPTIONS + actual request
- Optimize preflight cache with `Access-Control-Max-Age` (already default)
- Make sure OPTIONS requests are fast (~10ms)

❌ **DON'T:**
- Try to remove `Authorization` header (breaks auth)
- Try to disable CORS (breaks frontend)
- Retry on preflight failures (causes loops)

---

## 🚨 COMMON BUGS & FIXES

### Bug #1: Repeated API Calls

**BROKEN CODE:**
```typescript
useEffect(() => {
  fetchEmployees();
}, [isAuthenticated]); // ❌ Runs every time auth state changes
```

**FIXED CODE:**
```typescript
useEffect(() => {
  if (!isAuthenticated) return;
  fetchEmployees();
}, []); // ✅ Runs once on mount
```

---

### Bug #2: Infinite Redirect Loop

**BROKEN CODE:**
```typescript
// In axios response interceptor
if (error.response?.status === 403) {
  window.location.href = '/login'; // ❌ Hard redirect in interceptor
}
```

**FIXED CODE:**
```typescript
// In axios response interceptor
if (status === 401 || status === 403) {
  window.dispatchEvent(new CustomEvent('auth:unauthorized')); // ✅ Event-based
}

// In AuthContext
useEffect(() => {
  window.addEventListener('auth:unauthorized', logout);
}, []);
```

---

### Bug #3: Raw Axios Usage

**BROKEN CODE:**
```typescript
import axios from 'axios'; // ❌ Not the configured instance

const res = await axios.get('http://localhost:8080/api/admin/employees');
// No token, no interceptor → 403
```

**FIXED CODE:**
```typescript
import { employeeApi } from '@/lib/api'; // ✅ Use centralized API

const res = await employeeApi.getAll();
// Token added automatically by interceptor
```

---

### Bug #4: Stale Token

**BROKEN CODE:**
```typescript
const token = useState(localStorage.getItem('token')); // ❌ Only read once

axiosInstance.interceptors.request.use((config) => {
  config.headers.Authorization = `Bearer ${token}`; // Stale token!
});
```

**FIXED CODE:**
```typescript
axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('token'); // ✅ Read fresh every time
  config.headers.Authorization = `Bearer ${token}`;
});
```

---

## 📋 MIGRATION CHECKLIST

### Step 1: Replace Axios Configuration

- [ ] Delete all existing axios instances/configurations
- [ ] Copy `lib/axios.ts` to your project
- [ ] Update `baseURL` in axios.ts to your API URL

### Step 2: Replace AuthContext

- [ ] Delete existing AuthContext/AuthProvider
- [ ] Copy `contexts/AuthContext.tsx` to your project
- [ ] Wrap your app with `<AuthProvider>` in root layout

### Step 3: Create API Service Layer

- [ ] Copy `lib/api.ts` to your project
- [ ] Add/remove API functions based on your backend endpoints
- [ ] Update TypeScript types to match your DTOs

### Step 4: Update Components

- [ ] Find all `import axios from 'axios'` → Replace with `import api from '@/lib/api'`
- [ ] Find all `useEffect` with auth dependencies → Remove auth from deps
- [ ] Find all manual token handling → Remove (handled by interceptor)
- [ ] Use the example `page.tsx` pattern for all data-fetching pages

### Step 5: Test

- [ ] Login works → Token stored in localStorage
- [ ] Navigate to protected page → Single OPTIONS + Single GET request
- [ ] Logout works → Token cleared, redirected to login
- [ ] Expired token → Auto-logout and redirect
- [ ] Network tab shows clean request pattern (no loops)

---

## 🎓 ARCHITECTURE PRINCIPLES

### Separation of Concerns

```
┌──────────────────────────────────────────────────────────┐
│ BACKEND (Spring Boot)                                    │
│ - Business logic                                         │
│ - Data validation                                        │
│ - JWT generation & validation                            │
│ - Database operations                                    │
│ - Security rules                                         │
└──────────────────────────────────────────────────────────┘

┌──────────────────────────────────────────────────────────┐
│ FRONTEND (Next.js)                                       │
│ - UI rendering                                           │
│ - User interactions                                      │
│ - State management (UI state only)                       │
│ - Token storage (localStorage)                           │
│ - Error display                                          │
└──────────────────────────────────────────────────────────┘
```

### Single Responsibility

- **axios.ts**: HTTP client configuration only
- **AuthContext.tsx**: Auth state management only
- **api.ts**: API endpoint definitions only
- **Components**: UI rendering and user interactions only

### No Cross-Cutting Concerns

❌ **DON'T** put redirects in interceptors
❌ **DON'T** put data fetching in auth context
❌ **DON'T** put auth logic in components
✅ **DO** use event-based communication between layers

---

## 🐛 DEBUGGING GUIDE

### If API calls are still repeated:

1. **Check React Strict Mode:**
   ```typescript
   // next.config.js
   reactStrictMode: true // Components mount twice in dev mode
   ```
   **Solution:** Use `useRef` flag to prevent double fetch (see example)

2. **Check useEffect dependencies:**
   ```typescript
   useEffect(() => {
     // Should be empty or non-auth values
   }, []); // ← Check this array
   ```

3. **Check for multiple axios instances:**
   ```bash
   # Search your codebase
   grep -r "axios.create" src/
   grep -r "import axios from" src/
   ```
   **Solution:** Only use `axiosInstance` from `lib/axios.ts`

### If 403 errors persist:

1. **Check token in browser:**
   ```javascript
   // In browser console
   localStorage.getItem('token')
   ```

2. **Decode JWT:**
   - Copy token
   - Visit https://jwt.io
   - Check expiration (`exp` field)

3. **Check backend logs:**
   ```bash
   # Look for JWT validation errors
   grep "Error extracting username" backend.log
   ```

4. **Check Network tab:**
   - Request Headers → Should have `Authorization: Bearer ...`
   - Response → Should be 200 OK, not 403

---

## ✅ SUCCESS CRITERIA

Your fix is complete when:

- [ ] Login → Dashboard: **2 requests** (1 OPTIONS + 1 GET)
- [ ] Refresh page: **2 requests** (not 20+)
- [ ] Logout → Login: No errors in console
- [ ] Expired token → Auto-logout and clean redirect
- [ ] All protected endpoints work with same pattern
- [ ] Network tab shows clean pattern:
  ```
  OPTIONS → 200
  GET → 200
  (repeat for each unique endpoint, but NO loops)
  ```

---

## 📞 TROUBLESHOOTING

If you still see issues after implementing this fix:

1. **Clear browser cache and localStorage**
2. **Restart Spring Boot backend**
3. **Hard refresh frontend (Cmd+Shift+R / Ctrl+Shift+R)**
4. **Check BOTH browser console AND backend logs**
5. **Compare your code line-by-line with the examples**

---

## 🎉 FINAL NOTES

This architecture is:
- ✅ Production-ready
- ✅ Type-safe (TypeScript)
- ✅ Scalable (add new endpoints easily)
- ✅ Debuggable (clear separation of concerns)
- ✅ Maintainable (single source of truth)
- ✅ Performant (no unnecessary requests)

**The `/employees` endpoint worked by accident, probably using the configured axios instance. Other endpoints failed because they used raw axios or had retry logic. This fix ensures ALL endpoints work the same way.**
