import * as React from "react";

import PageTemplate from "components/templates/PageTemplate";

interface ContentsProps {
  className: string;
}

interface ContentsState {
  index: number;
}

class CoinInfo extends React.Component<ContentsProps, ContentsState> {
  private static TAB_HEADERS = [];
  private static TAB_ITEMS = [];

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
      <PageTemplate
        className={this.props.className}
        index={this.state.index}
        onChangeTab={this.onChangeTabIndex}
        tabHeaders={CoinInfo.TAB_HEADERS}
        tabContents={CoinInfo.TAB_ITEMS}
      />
    );
  }
}

export default CoinInfo;
