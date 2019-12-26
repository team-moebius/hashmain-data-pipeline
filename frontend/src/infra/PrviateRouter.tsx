import * as React from 'react';
import { Redirect, Route, RouteProps } from 'react-router-dom';

interface PrivateRouteProps extends RouteProps {
  redirectPath: string;
  signing: boolean;
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ component, signing, redirectPath, ...rest }) => {
  const render = (Component: any) => (props: RouteProps) =>
    signing ? <Component {...props} /> : <Redirect to={{ pathname: redirectPath, state: { from: props.location } }} />;

  return <Route {...rest} render={render(component)} />;
};

export default PrivateRoute;
