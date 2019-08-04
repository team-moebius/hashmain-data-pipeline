import axios from 'axios';
import { push } from 'connected-react-router';

const BASE_URL = 'http://52.79.86.26/api';

const ajax = axios.create({
  baseURL: BASE_URL,
  // baseURL: 'http://api-dev.cryptoboxglobal.com/api/',
  responseType: 'json',
});

const setAjaxJwtHeader = (jwtHeader: String) => {
  ajax.defaults.headers.common['Authorization'] = `Bearer ${jwtHeader}`;
};

const addSignOutInterceptor = (dispatchFunc: any, signOutFunc: any) => {
  ajax.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      const isSignInRequest =
        error.config.url === `${BASE_URL}/members` && error.config.method === 'post';
      if (
        error.response &&
        error.response.status === 401 &&
        !isSignInRequest // Login
      ) {
        // 로그인을 제외한 일반적인 401 error시에는 login form으로 forward
        alert('세션 만료. 재로그인 해주세요.');
        dispatchFunc(signOutFunc());
        push('http://localhost:3000/sign');
      }
      return Promise.reject(error);
    }
  );
};

export { setAjaxJwtHeader, addSignOutInterceptor };
export default ajax;
