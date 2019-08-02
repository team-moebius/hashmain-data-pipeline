import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { connectRouter } from 'connected-react-router';
import { History } from 'history';

import page, { PageReduxState } from 'pages/PageWidgets';

/**
 * [Intellisense의 Auto complete 지원을 위한 interface]
 *
 * 모든 Redux의 State들의 아래 interface에 모이고,
 * 각 Components에서는 해당 State를 호출하여
 * Redux에 존재하는 모든 state들의 자동완성을 지원 받을 수 있게 됩니다.
 */
export interface ReduxState {
  page: PageReduxState;
}
/**
 * 모든 Reducer가 개발 완료된 뒤에는, 아래 Root reducer에 reducer의 레퍼런스를 넣어주게 됩니다.
 * 해당 작업을 통해 Reducer는 Redux의 관리하에 들어갑니다.
 */
const rootReducer = (routeHistory: History) =>
  combineReducers({
    page,
    router: connectRouter(routeHistory),
  });
/**
 * SPA 아키텍처는 새로고침에 대한 Redux state들의 상태 보존을 지원해 주지 않습니다.
 * react-persist를 통해 새로고침을 하더라도, 현재 존재하는 redux 상태들을
 * 보존할 수 있게 됩니다.
 */
export const persistConfig = {
  storage,
  key: 'root',
  blacklist: ['page'],
};

export default (routeHistory: History) => persistReducer(persistConfig, rootReducer(routeHistory));
