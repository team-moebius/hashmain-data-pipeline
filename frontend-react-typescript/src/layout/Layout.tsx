import * as React from 'react';

import Header from 'layout/Header';
import Menu from 'layout/Menu';

import HtsConfig from 'pages/contents/HtsConfig';
import AssetManagement from 'pages/contents/AssetManagement';
import Idea from 'pages/contents/Idea';
import CoinInfo from 'pages/contents/CoinInfo';
import UseGuide from 'pages/contents/UseGuide';
import Profile from 'pages/contents/Profile';

import 'layout/Layout.scss';

interface LayoutProps {}
interface LayoutState {
  index: number;
}
// TODO: Refactoring for usable icons later
class Layout extends React.Component<LayoutProps, LayoutState> {
  static readonly MENU_ITEMS: string[] = [
    'HTS 설정',
    '자산관리',
    '아이디어',
    '코인정보',
    '이용안내',
    '프로필',
  ];

  constructor(props: LayoutProps) {
    super(props);
    this.state = {
      index: 0,
    };
  }

  onChangeMenuIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    return (
      <div className="layout">
        <Header />
        <div className="layout-contents">
          <Menu
            className="layout-contents__menu"
            index={this.state.index}
            items={Layout.MENU_ITEMS}
            onChange={this.onChangeMenuIndex}
          />
          <div className="layout-contents__item-wrapper">
            {this.state.index === 0 && <HtsConfig />}
            {this.state.index === 1 && <AssetManagement />}
            {this.state.index === 2 && <Idea />}
            {this.state.index === 3 && <CoinInfo />}
            {this.state.index === 4 && <UseGuide />}
            {this.state.index === 5 && <Profile />}
          </div>
        </div>
      </div>
    );
  }
}

export default Layout;
