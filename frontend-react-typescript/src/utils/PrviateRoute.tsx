import * as React from 'react';
import { Redirect, Route, RouteProps } from 'react-router-dom';

interface PrivateRouteProps extends RouteProps {
  redirectPath: string;
  signing: boolean;
}

const PrivateRoute: React.SFC<PrivateRouteProps> = ({
  component,
  signing,
  redirectPath,
  ...rest
}) => {
  const renderFn = (Component: any) => (props: RouteProps) => {
    if (signing) return <Component {...props} />;

    const redirectProps = {
      to: {
        pathname: redirectPath,
        state: { from: props.location },
      },
    };
    return <Redirect {...redirectProps} />;
  };

  return <Route {...rest} render={renderFn(component)} />;
};

export default PrivateRoute;
