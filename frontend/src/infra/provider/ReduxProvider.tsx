import React from 'react';
import { Provider } from 'react-redux';
import { ConnectedRouter } from 'connected-react-router';
import { PersistGate } from 'redux-persist/integration/react';

import setReduxStore from 'infra/redux/GlobalStore';
import RouteHistory from 'infra/RouteHistory';

interface ReduxProivderProps {
  children: React.ReactNode;
}

const mainStore = setReduxStore();

const ReduxProvider: React.FC<ReduxProivderProps> = props => (
  <Provider store={mainStore.store}>
    <PersistGate loading={null} persistor={mainStore.persistor}>
      <ConnectedRouter history={RouteHistory.instance}>{props.children}</ConnectedRouter>
    </PersistGate>
  </Provider>
);

export default ReduxProvider;
