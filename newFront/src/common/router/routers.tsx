import * as React from 'react'
import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Home from '../../components/home'
import Test from '../../components/test'
import Sign from '../../components/sign/sign'
import NotFound from '../../components/notFound'

export const Routers: React.StatelessComponent = ():JSX.Element => (
  <Router>
    <Switch>
      <Route exact={true} path='/' component={Home} />
      <Route path='/sign' component={Sign} />
      <Route path='/Test' component={Test} />
      <Route component={NotFound} />
    </Switch>
  </Router>
)
