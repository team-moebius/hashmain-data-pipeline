import * as React from 'react';
import { withAlert, AlertManager } from 'react-alert';
import classNames from 'classnames';

import Paper from 'components/atoms/Paper';
import Text from 'components/atoms/Text';
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

  render() {
    return (
      <>
        <PageTemplate
          className={classNames('hts-config__tab', this.props.className)}
          index={this.state.index}
          onChangeTab={this.onChangeTabIndex}
          tabHeaders={HtsConfig.TAB_HEADERS}
          tabContents={HtsConfig.TAB_ITEMS}
        />
        <Paper className="hts-config__sub">{123}</Paper>
      </>
    );
  }
}

// @ts-ignore
export default withAlert()(HtsConfig);
