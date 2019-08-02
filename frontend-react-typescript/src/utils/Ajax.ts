import axios from 'axios';
import { push } from 'connected-react-router';

const Ajax = axios.create({
  baseURL: 'http://52.79.86.26/api/',
  responseType: 'json',
});

Ajax.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    if (error.response === 401) {
      // TODO: Redirect 401
      console.log('It can be redirect');
      push('http://localhost:3000/sign');
    }
    return Promise.reject(error);
  }
);

export default Ajax;
