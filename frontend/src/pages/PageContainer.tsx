import React from 'react';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import { connect } from 'react-redux';

import SignPage from 'pages/main/UserIntroPage';
import MainPage from 'pages/main/MainPage';
import PrivateRoute from 'infra/PrviateRouter';
import { ReduxState } from 'infra/redux/GlobalState';

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
