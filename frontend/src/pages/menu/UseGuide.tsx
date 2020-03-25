import * as React from "react";

import PageTemplate from "components/templates/PageTemplate";
import Test1 from "pages/sub/Test1";
import Test2 from "pages/sub/Test2";

interface ContentsProps {
  className: string;
}

interface ContentsState {
  index: number;
}

class UseGuide extends React.Component<ContentsProps, ContentsState> {
  private static TAB_HEADERS = [<>로드맵 소개</>, <>개인 정보 정책</>];
  // eslint-disable-next-line react/jsx-key
  private static TAB_ITEMS = [<Test1/>, <Test2/>];

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
        tabHeaders={UseGuide.TAB_HEADERS}
        tabContents={UseGuide.TAB_ITEMS}
      />
    );
  }
}

export default UseGuide;
