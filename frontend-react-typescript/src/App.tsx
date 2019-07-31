import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router';
import {
  transitions,
  positions,
  Provider as AlertProvider,
  AlertComponentPropsWithStyle,
} from 'react-alert';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

import AlertContents from 'components/molecules/AlertContents';
import SignPage from 'pages/entry/SignPage';
import MainPage from 'pages/entry/MainPage';
import PrivateRoute from 'utils/PrviateRoute';
import setReduxStore, { routeHistory } from 'utils/GlobalStore';

/** Material-ui theme setting */
const defaultTheme = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      main: '#13253F',
      light: '#173456',
    },
    secondary: {
      main: '#1E8CDE',
    },
  },
  typography: {
    fontSize: 11,
  },
});

/** Alert setting */
const alertOptions = {
  position: positions.BOTTOM_CENTER,
  timeout: 40000,
  offset: '30px',
  transition: transitions.FADE,
};

/** Alert template */
const AlertTemplate = ({ style, options, message, close }: AlertComponentPropsWithStyle) => (
  <div style={style}>
    <AlertContents variant={options.type || 'info'} message={message} onClose={close} />
  </div>
);

/** Get Redux Store */
const mainStore = setReduxStore();

/** App의 기본 설정들을 위한 Wrapper들을 적용시키는 Functional Component */
const AppWrapper: React.SFC<{}> = props => (
  <Provider store={mainStore.store}>
    <PersistGate loading={null} persistor={mainStore.persistor}>
      <MuiThemeProvider theme={defaultTheme}>
        <CssBaseline>
          <AlertProvider template={AlertTemplate} {...alertOptions}>
            <ConnectedRouter history={routeHistory}>{props.children}</ConnectedRouter>
          </AlertProvider>
        </CssBaseline>
      </MuiThemeProvider>
    </PersistGate>
  </Provider>
);

const AppEntry = () => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={false} />
    </Switch>
  </Router>
);

const App: React.FC = () => (
  <AppWrapper>
    <AppEntry />
  </AppWrapper>
);

export default App;
