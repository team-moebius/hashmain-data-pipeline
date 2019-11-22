import { applyMiddleware, createStore } from 'redux';
import { persistStore } from 'redux-persist';
import { routerMiddleware } from 'connected-react-router';
import { composeWithDevTools } from 'redux-devtools-extension/developmentOnly';

import createRootReducer from 'infra/redux/GlobalReducer';
import RouteHistory from 'infra/RouteHistory';

const store = createStore(
  createRootReducer(RouteHistory.instance),
  // preload state,
  composeWithDevTools(applyMiddleware(routerMiddleware(RouteHistory.instance)))
);
const persistor = persistStore(store);

export default () => {
  return { store, persistor };
};
