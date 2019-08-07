import axios from 'axios';
import { push } from 'connected-react-router';

// please refer ip directly rather dns cause of price issue.
const CONNECTION_INFO = {
  local: 'http://127.0.0.1',
  develop: 'http://52.79.86.26',
  production: 'http://52.78.49.241',
};

const ajax = axios.create({
  baseURL: CONNECTION_INFO.develop,
  // baseURL: 'http://api-dev.cryptoboxglobal.com/',
  responseType: 'json',
});

const interceptors = {
  request: 0,
  response: 0,
};

const ejectInterceptors = () => {
  const { request, response } = interceptors;
  ajax.interceptors.request.eject(request);
  ajax.interceptors.response.eject(response);
};

const addJwtTokenInterceptor = (jwtToken: any) => {
  const curInterceptor = interceptors.request;
  if (curInterceptor) ajax.interceptors.request.eject(curInterceptor);

  const newInterceptor = ajax.interceptors.request.use(
    config => {
      ajax.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
      console.log(ajax.defaults.headers.common['Authorization']);
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
  if (curInterceptor) ajax.interceptors.response.eject(curInterceptor);

  const newInterceptor = ajax.interceptors.response.use(
    response => {
      return response;
    },
    error => {
      const isSignInRequest =
        error.config.url === `${CONNECTION_INFO.develop}/members` && error.config.method === 'post';

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

export { addSignOutInterceptor, addJwtTokenInterceptor, ejectInterceptors };
export default ajax;
