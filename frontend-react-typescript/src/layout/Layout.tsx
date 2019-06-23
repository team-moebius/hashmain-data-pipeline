import * as React from 'react';

import Header from 'layout/Header';

import 'layout/Layout.scss';

interface LayoutProps {}

const Layout: React.FunctionComponent<LayoutProps> = props => {
  return (
    <div className="layout">
      <Header />
    </div>
  );
};

export default Layout;
