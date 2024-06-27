import axios from 'axios';

const API_URL = 'http://127.0.0.1:8080';

const api = axios.create({
  baseURL: API_URL,
  mode: 'no-cors',
  headers: {'Content-Type': 'application/json'},
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('authToken');
    if (token) {
      config.headers.Authorization = `${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export const login = (credentials) => api.post('/users/login', credentials);
export const register = (credentials) => api.post('/users/register', credentials);
export const fetchUsers = () => api.get('/users');
export const toggleUserActivation = (userId) => api.post(`/users/${userId}/toggle-activation`);

export default api;
