import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';

import SignPage from 'pages/SignPage';
import MainPage from 'pages/MainPage';

import 'assets/scss/layout.scss';
import PrivateRoute from 'utils/PrviateRoute';

const AppEntry = () => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={false} />
    </Switch>
  </Router>
);

const App: React.FC = () => <AppEntry />;

export default App;
