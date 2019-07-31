import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import {
  transitions,
  positions,
  Provider as AlertProvider,
  AlertComponentPropsWithStyle,
} from 'react-alert';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

import SignPage from 'pages/entry/SignPage';
import MainPage from 'pages/entry/MainPage';
import PrivateRoute from 'utils/PrviateRoute';
import AlertContents from 'components/molecules/AlertContents';

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

//TODO: connect with redux or mobx(signing props)
const AppEntry = () => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={false} />
    </Switch>
  </Router>
);

const App: React.FC = () => (
  <MuiThemeProvider theme={defaultTheme}>
    <CssBaseline>
      <AlertProvider template={AlertTemplate} {...alertOptions}>
        <AppEntry />
      </AlertProvider>
    </CssBaseline>
  </MuiThemeProvider>
);

export default App;
