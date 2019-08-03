import * as React from 'react';
import ApiKeyRegistBox from 'components/anti/ApiKeyRegistBox';
import Paper from 'components/atoms/Paper';

import 'assets/scss/HtsConfig.scss';

interface HtsConfigProps {}

interface HtsConfigState {}

class HtsConfig extends React.Component<HtsConfigProps, HtsConfigState> {
  onClickApiRegistButton = () => {};

  render() {
    return (
      <div>
        <Paper className="api-key-box">
          <ApiKeyRegistBox onClickRegistApiKeyButton={this.onClickApiRegistButton} />
        </Paper>
      </div>
    );
  }
}

export default HtsConfig;
