import axios from 'axios';
import { push } from 'connected-react-router';

// please refer ip directly rather dns cause of price issue.
const CONNECTION_INFO = {
  local: 'http://127.0.0.1',
  develop: 'http://52.79.86.26',
  production: 'http://52.78.49.241',
};

const ajax = axios.create({
  baseURL: CONNECTION_INFO.production,
  // baseURL: 'http://api-dev.cryptoboxglobal.com/',
  responseType: 'json',
});

const curInterceptors = {
  request: 0,
  response: 0,
};

const ejectInterceptors = () => {
  const { request, response } = curInterceptors;
  ajax.interceptors.request.eject(request);
  ajax.interceptors.response.eject(response);
};

const addJwtTokenInterceptor = (jwtToken: any) => {
  const curInterceptor = curInterceptors.request;
  ajax.interceptors.request.eject(curInterceptor);

  curInterceptors.request = ajax.interceptors.request.use(
    config => {
      config.headers['Authorization'] = `Bearer ${jwtToken}`;
      return config;
    },
    error => {
      return Promise.reject(error);
    }
  );
};

const addSignOutInterceptor = (signOutFunc: any) => {
  const curInterceptor = curInterceptors.response;
  ajax.interceptors.response.eject(curInterceptor);
  curInterceptors.response = ajax.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      const isSignInRequest =
        error.config.url === `${CONNECTION_INFO.develop}/api/members` && error.config.method === 'post';
      const noResponse = !error.response;
      const expired = error.response && error.response.status === 401 && !isSignInRequest;

      if (noResponse || expired) {
        signOutFunc();
        alert('세션 만료. 재로그인 해주세요.');
        push('http://localhost:3000/sign');
      }
      return Promise.reject(error);
    }
  );
};

export { addSignOutInterceptor, addJwtTokenInterceptor, ejectInterceptors };
export default ajax;
