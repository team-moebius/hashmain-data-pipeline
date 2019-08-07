import * as React from 'react';
import { withAlert, AlertManager } from 'react-alert';

import ApiKeyRegistBox from 'components/organisms/ApiKeyRegistBox';
import Paper from 'components/atoms/Paper';
import ajax from 'utils/Ajax';

import 'assets/scss/HtsConfig.scss';

interface HtsConfigProps {
  alert: AlertManager;
}

interface HtsConfigState {}

class HtsConfig extends React.Component<HtsConfigProps, HtsConfigState> {
  onClickApiRegistButton = (data: object) => {
    console.log(data);
    ajax
      .post('/api-keys', data)
      .then(reponse => {
        this.props.alert.success('등록 성공');
      })
      .catch(error => {
        this.props.alert.error('등록 실패');
      });
  };

  onClickViewMyApkKeyButton = () => {
    ajax
      .get('')
      .then(reponse => {
        // this.props.alert.success('등록 성공');
      })
      .catch(error => {
        // this.props.alert.error('등록 실패');
      });
  };

  render() {
    return (
      <div>
        <Paper className="api-key-box">
          <ApiKeyRegistBox
            onClickRegistApiKeyButton={this.onClickApiRegistButton}
            onClickViewMyApiKeyButton={this.onClickViewMyApkKeyButton}
          />
        </Paper>
      </div>
    );
  }
}

export default withAlert()(HtsConfig);
