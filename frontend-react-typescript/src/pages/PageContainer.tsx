import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';

import SignPage from 'pages/entry/SignPage';
import MainPage from 'pages/entry/MainPage';
import PrivateRoute from 'utils/PrviateRoute';
import { ReduxState } from 'utils/GlobalReducer';

interface PageProps {
  signing: boolean;
}

const PageEntry = ({ signing }: PageProps) => (
  <Router>
    <Switch>
      <Route component={SignPage} path="/sign" />
      <PrivateRoute component={MainPage} path="/" redirectPath="/sign" signing={signing} />
    </Switch>
  </Router>
);

const PageContainer: React.FC<PageProps> = props => <PageEntry signing={props.signing} />;

const mapStateToProps = (state: ReduxState) => ({
  signing: state.page.signing,
});

export default connect(mapStateToProps)(PageContainer);
