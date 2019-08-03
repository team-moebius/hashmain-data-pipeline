import * as React from 'react';
import ApiKeyRegistBox from 'components/anti/ApiKeyRegistBox';
import Paper from 'components/atoms/Paper';

import 'assets/scss/HtsConfig.scss';

interface HtsConfigProps {}

const HtsConfig: React.FunctionComponent<HtsConfigProps> = props => {
  return (
    <div>
      HtsConfig!
      <Paper className="api-key-box">
        <ApiKeyRegistBox />
      </Paper>
    </div>
  );
};

export default HtsConfig;
