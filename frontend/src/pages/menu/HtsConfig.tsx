import * as React from "react";
import { withAlert, AlertManager } from "react-alert";
import classNames from "classnames";

import Paper from "components/atoms/Paper";
import Text from "components/atoms/Text";
import PageTemplate from "components/templates/PageTemplate";
import HtsConfigGrid from "pages/sub/HtsConfigGrid";
import CoinClass from "pages/sub/CoinClass";

import "assets/scss/HtsConfig.scss";

interface ContentsProps {
  className: string;
  alert: AlertManager;
}

interface ContentsState {
  index: number;
}

class HtsConfig extends React.Component<ContentsProps, ContentsState> {
  // eslint-disable-next-line react/jsx-key
  private static TAB_HEADERS = [<Text variant="subtitle2">멀티거래 모드</Text>];
  // eslint-disable-next-line react/jsx-key
  private static TAB_ITEMS = [<HtsConfigGrid/>];

  public constructor(props: ContentsProps) {
    super(props);
    this.state = {
      index: 0,
    };
  }

  private onChangeTabIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  public render() {
    return (
      <>
        <PageTemplate
          className={classNames("hts-config__tab", this.props.className)}
          index={this.state.index}
          onChangeTab={this.onChangeTabIndex}
          tabHeaders={HtsConfig.TAB_HEADERS}
          tabContents={HtsConfig.TAB_ITEMS}
        />
        <Paper className="hts-config__sub">
          <CoinClass/>
        </Paper>
      </>
    );
  }
}

// @ts-ignore
export default withAlert()(HtsConfig);
