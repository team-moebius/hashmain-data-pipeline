import React from 'react';
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
import PageContainer from 'pages/PageContainer';
import setReduxStore, { routeHistory } from 'utils/GlobalStore';
import { addSignOutInterceptor } from 'utils/Ajax';
import { actionCreators as pageActions } from 'pages/PageWidgets';

/** Material-ui theme setting */
const defaultTheme = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      main: '#13253F',
      light: '#173456',
      contrastText: '#C9CFE8',
    },
    secondary: {
      main: '#1E8CDE',
    },
  },
  typography: {
    fontSize: 12,
    body1: { fontSize: 12 },
    h6: { fontSize: 14 },
  },
});

/** Alert setting */
const alertOptions = {
  position: positions.BOTTOM_CENTER,
  timeout: 3000,
  offset: '15px 0px 15px 0px',
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

addSignOutInterceptor(mainStore.store.dispatch, pageActions.signOut);

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

const App: React.FC = () => (
  <AppWrapper>
    <PageContainer />
  </AppWrapper>
);

export default App;
