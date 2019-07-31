import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: 'http://52.79.86.26/api/',
  responseType: 'json',
});

axiosInstance.interceptors.response.use(
  response => response,
  error => {
    if (error.response.status === 401) {
      // TODO: Redirect 401
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
