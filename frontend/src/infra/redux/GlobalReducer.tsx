import { combineReducers } from 'redux';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { History } from 'history';
import { connectRouter } from 'connected-react-router';

import page from 'pages/PageWidgets';

// Every reducer is bound with Root reducer as reference.
const rootReducer = (routeHistory: History) =>
  combineReducers({
    page,
    router: connectRouter(routeHistory),
  });

// SPA architecture doesn't guarantee redux state about page refresh.
// By refreshing through react-persist, the current redux state is kept well.
export const persistConfig = {
  storage,
  key: 'root',
  blacklist: [],
};

export default (routeHistory: History) => persistReducer(persistConfig, rootReducer(routeHistory));
