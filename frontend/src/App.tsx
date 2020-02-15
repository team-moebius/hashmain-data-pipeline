import React from 'react';

import AlertProvider from 'infra/provider/AlertProvider';
import ThemeProvider from 'infra/provider/ThemeProvider';
import ReduxProvider from 'infra/provider/ReduxProvider';
import PageContainer from 'pages/PageContainer';

/** App의 기본 설정들을 위한 Provider 를 적용시키는 Functional Component */
const AppWrapper: React.FC<{}> = props => (
  <ReduxProvider>
    <ThemeProvider>
      <AlertProvider>{props.children}</AlertProvider>
    </ThemeProvider>
  </ReduxProvider>
);

const App: React.FC = () => (
  <AppWrapper>
    <PageContainer />
  </AppWrapper>
);

export default App;
