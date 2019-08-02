import * as React from 'react';
import { push } from 'connected-react-router';
import MuiButton from '@material-ui/core/Button';
import MuiTypography from '@material-ui/core/Typography';

import AppBar from 'components/molecules/AppBar';
import VerticalTabs from 'components/molecules/VerticalTabs';

import HtsConfig from 'pages/contents/HtsConfig';
import AssetManagement from 'pages/contents/AssetManagement';
import Idea from 'pages/contents/Idea';
import CoinInfo from 'pages/contents/CoinInfo';
import UseGuide from 'pages/contents/UseGuide';
import Profile from 'pages/contents/Profile';
import Ajax from 'utils/Ajax';

import bgImage from 'assets/images/bg.png';
import logo from 'assets/images/logo.png';
import 'assets/scss/MainPage.scss';

interface MainPageProps {
  onClickAlertSample?: (e: React.MouseEvent<HTMLElement>) => void;
}
interface MainPageState {
  index: number;
}

// TODO: Refactoring for usable icons later
class MainPage extends React.Component<MainPageProps, MainPageState> {
  static readonly MENU_ITEMS: string[] = [
    'HTS 설정',
    '자산관리',
    '아이디어',
    '코인정보',
    '이용안내',
    '프로필',
  ];

  constructor(props: MainPageProps) {
    super(props);
    this.state = {
      index: 0,
    };
  }

  onClickSignOut = () => {
    push('http://localhost:3000/sign');
  };

  onChangeMenuIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    console.log(Ajax.defaults.headers.common['Authorization']);
    return (
      <div style={{ backgroundImage: bgImage }} className="layout">
        <AppBar
          className="layout-header"
          position="absolute"
          subTitle={
            <>
              <em>H</em>ome <em>T</em>rading <em>S</em>ystem
            </>
          }
          title="CRYPTO BOX GLOBAL."
        >
          {{
            leftSide: <img alt="logo" className="layout-header__logo" src={logo} />,
            rightSide: (
              <>
                <MuiButton
                  className="layout-header__button"
                  size="medium"
                  onClick={this.props.onClickAlertSample}
                >
                  <MuiTypography variant="h6">얼럿 샘플</MuiTypography>
                </MuiButton>
                <MuiButton
                  className="layout-header__button"
                  size="medium"
                  onClick={this.onClickSignOut}
                >
                  <MuiTypography variant="h6">로그 아웃</MuiTypography>
                </MuiButton>
              </>
            ),
          }}
        </AppBar>
        <div className="layout-contents">
          <VerticalTabs
            className="layout-contents__menu"
            index={this.state.index}
            items={MainPage.MENU_ITEMS}
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

export default MainPage;
