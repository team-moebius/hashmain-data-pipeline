import * as React from 'react';
import { withAlert, AlertManager } from 'react-alert';
import classNames from 'classnames';

import ajax from 'utils/Ajax';
import Paper from 'components/atoms/Paper';
import Text from 'components/atoms/Text';
import ApiKeyRegistBox from 'components/organisms/ApiKeyRegistBox';
import PageTemplate from 'components/templates/PageTemplate';
import MultiTradingMode from 'pages/sub/MultiTradingMode';

import 'assets/scss/HtsConfig.scss';

interface ContentsProps {
  className: string;
  alert: AlertManager;
}

interface ContentsState {
  index: number;
}

class HtsConfig extends React.Component<ContentsProps, ContentsState> {
  private static TAB_HEADERS = [<Text variant="subtitle2">멀티거래 모드</Text>];
  private static TAB_ITEMS = [<MultiTradingMode />];
  constructor(props: ContentsProps) {
    super(props);
    this.state = {
      index: 0,
    };
  }

  onChangeTabIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  onSubmitApiRegist = (data: object) => {
    ajax
      .post('/api/api-keys', data)
      .then(response => {
        this.props.alert.success('등록 성공');
      })
      .catch(error => {
        this.props.alert.error('등록 실패');
      });
  };

  onClickViewMyApkKeyButton = () => {
    ajax
      .get('/api')
      .then(reponse => {
        // this.props.alert.success('등록 성공');
      })
      .catch(error => {
        // this.props.alert.error('등록 실패');
      });
  };

  render() {
    return (
      <div className="hts-config">
        <PageTemplate
          className={classNames('hts-config__tab', this.props.className)}
          index={this.state.index}
          onChangeTab={this.onChangeTabIndex}
          tabHeaders={HtsConfig.TAB_HEADERS}
          tabContents={HtsConfig.TAB_ITEMS}
        />
        <Paper className="hts-config__sub">
          <ApiKeyRegistBox
            onSubmit={this.onSubmitApiRegist}
            onClickViewMyApiKeyButton={this.onClickViewMyApkKeyButton}
          />
        </Paper>
      </div>
    );
  }
}

// @ts-ignore
export default withAlert()(HtsConfig);
