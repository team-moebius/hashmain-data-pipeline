import * as React from 'react';
import { withAlert, AlertManager } from 'react-alert';
import classNames from 'classnames';

import Paper from 'components/atoms/Paper';
import ApiKeyRegistBox from 'components/organisms/ApiKeyRegistBox';
import PageTemplate from 'components/templates/PageTemplate';
import Test1 from 'pages/sub-contents/Test1';
import Test2 from 'pages/sub-contents/Test2';
import ajax from 'utils/Ajax';

import 'assets/scss/HtsConfig.scss';

interface ContentsProps {
  className: string;
  alert: AlertManager;
}

interface ContentsState {
  index: number;
}

class HtsConfig extends React.Component<ContentsProps, ContentsState> {
  private static TAB_HEADERS = [<>멀티거래 모드</>, <>Test2</>];
  private static TAB_ITEMS = [<Test1 />, <Test2 />];
  constructor(props: ContentsProps) {
    super(props);
    this.state = {
      index: 0,
    };
  }

  onChangeTabIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  onClickApiRegistButton = (data: object) => {
    console.log(data);
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
            onClickRegistApiKeyButton={this.onClickApiRegistButton}
            onClickViewMyApiKeyButton={this.onClickViewMyApkKeyButton}
          />
        </Paper>
      </div>
    );
  }
}

// @ts-ignore
export default withAlert()(HtsConfig);
