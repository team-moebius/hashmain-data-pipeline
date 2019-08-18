import * as React from 'react';

import PageTemplate from 'components/templates/PageTemplate';
import Test1 from 'pages/sub-contents/Test1';
import Test2 from 'pages/sub-contents/Test2';

interface ContentsProps {
  className: string;
}

interface ContentsState {
  index: number;
}

class Assets extends React.Component<ContentsProps, ContentsState> {
  private static TAB_HEADERS = ['자산 보유내역', '주문 완료내역'];
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

  render() {
    return (
      <PageTemplate
        className={this.props.className}
        index={this.state.index}
        onChangeTab={this.onChangeTabIndex}
        tabHeaders={Assets.TAB_HEADERS}
        tabContents={Assets.TAB_ITEMS}
      />
    );
  }
}

export default Assets;
