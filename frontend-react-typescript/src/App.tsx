import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

import SignPage from 'pages/SignPage';
import MainPage from 'pages/MainPage';
import PrivateRoute from 'utils/PrviateRoute';

/** Material-ui theme setting */
const defaultTheme = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      main: '#13253F',
    },
    secondary: {
      main: '#1E8CDE',
    },
  },
  typography: {
    fontSize: 11,
  },
});

//TODO: connect with redux or mobx(signing props)
const AppEntry = () => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={true} />
    </Switch>
  </Router>
);

const App: React.FC = () => (
  <MuiThemeProvider theme={defaultTheme}>
    <CssBaseline>
      <AppEntry />
    </CssBaseline>
  </MuiThemeProvider>
);

export default App;
