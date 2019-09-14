import { applyMiddleware, createStore } from 'redux';
import { persistStore } from 'redux-persist';
import { routerMiddleware } from 'connected-react-router';
import { createBrowserHistory } from 'history';
import { composeWithDevTools } from 'redux-devtools-extension/developmentOnly';

import createRootReducer from 'utils/GlobalReducer';

// Create browser history for react-router
export const routeHistory = createBrowserHistory();
const store = createStore(
  createRootReducer(routeHistory),
  // connectRouter(routeHistory)(createRootReducer) /* preload state */,
  composeWithDevTools(applyMiddleware(routerMiddleware(routeHistory)))
);
const persistor = persistStore(store);

export default () => {
  return { store, persistor };
};
