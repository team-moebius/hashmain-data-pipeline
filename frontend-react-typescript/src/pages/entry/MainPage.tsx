import * as React from 'react';
import Layout from 'layout/Layout';

import bgImage from 'assets/images/bg.png';

interface MainPageProps {}

const MainPage: React.FC<MainPageProps> = props => {
  return (
    <div style={{ backgroundImage: `url(${bgImage})` }}>
      <Layout />
    </div>
  );
};

export default MainPage;
