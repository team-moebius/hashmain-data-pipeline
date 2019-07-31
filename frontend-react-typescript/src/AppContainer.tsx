import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';

import SignPage from 'pages/entry/SignPage';
import MainPage from 'pages/entry/MainPage';
import PrivateRoute from 'utils/PrviateRoute';

const AppEntry = () => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={true} />
    </Switch>
  </Router>
);

const AppContainer: React.FC = () => <AppEntry />;

const mapStateToProps = () => ({});

export default connect(mapStateToProps)(AppContainer);
