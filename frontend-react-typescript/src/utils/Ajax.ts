import axios from 'axios';
import { push } from 'connected-react-router';

const BASE_URL = 'http://52.79.86.26/api';

const ajax = axios.create({
  baseURL: BASE_URL,
  // baseURL: 'http://api-dev.cryptoboxglobal.com/api/',
  responseType: 'json',
});

interface interceptorsList {
  request?: number;
  response?: number;
}

const interceptors: interceptorsList = {
  request: undefined,
  response: undefined,
};

const setAjaxJwtHeader = (jwtHeader: String) => {
  ajax.defaults.headers.common['Authorization'] = `Bearer ${jwtHeader}`;
};

const addJwtTokenInterceptor = (jwtToken: any) => {
  const curInterceptor = interceptors.request;
  if (curInterceptor && Number.isInteger(curInterceptor))
    ajax.interceptors.request.eject(curInterceptor);

  const newInterceptor = ajax.interceptors.request.use(
    config => {
      console.log(jwtToken);
      ajax.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );
  interceptors.request = newInterceptor;
};

const addSignOutInterceptor = (signOutFunc: any) => {
  const curInterceptor = interceptors.response;
  if (curInterceptor && Number.isInteger(curInterceptor))
    ajax.interceptors.response.eject(curInterceptor);

  const newInterceptor = ajax.interceptors.response.use(
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
        signOutFunc();
        push('http://localhost:3000/sign');
      }
      return Promise.reject(error);
    }
  );
  interceptors.response = newInterceptor;
};

export { setAjaxJwtHeader, addSignOutInterceptor, addJwtTokenInterceptor };
export default ajax;
