import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';

import MuiButton from '@material-ui/core/Button';

import Text from 'components/atoms/Text';
import AppBar from 'components/molecules/AppBar';
import Tab from 'components/molecules/Tab';
import HtsConfig from 'pages/menu-contents/HtsConfig';
import Assets from 'pages/menu-contents/Assets';
import Idea from 'pages/menu-contents/Idea';
import CoinInfo from 'pages/menu-contents/CoinInfo';
import UseGuide from 'pages/menu-contents/UseGuide';
import Profile from 'pages/menu-contents/Profile';
import { actionCreators as pageActions } from 'pages/PageWidgets';
import { addSignOutInterceptor, addJwtTokenInterceptor, ejectInterceptors } from 'utils/Ajax';
import { ReduxState } from 'infra/redux/GlobalState';

import bgImage from 'assets/images/bg.png';
import logo from 'assets/images/logo.png';
import 'assets/scss/MainPage.scss';

interface StateProps {
  signing: boolean;
  token: string;
}

interface DispatchProps {
  signOut: () => void;
}

interface MainPageProps extends StateProps, DispatchProps {}

interface MainPageState {
  index: number;
}

class MainPage extends React.Component<MainPageProps, MainPageState> {
  static readonly MENU_ITEMS: JSX.Element[] = [
    <>HTS 설정</>,
    <>자산관리</>,
    <>아이디어</>,
    <>코인정보</>,
    <>이용안내</>,
    <>프로필</>,
  ];

  constructor(props: MainPageProps) {
    super(props);
    this.state = {
      index: 0,
    };

    addSignOutInterceptor(this.props.signOut);
    addJwtTokenInterceptor(this.props.token);
  }

  onClickAlertSample? = (e: React.MouseEvent<HTMLElement>) => {};

  onClickSignOut = () => {
    ejectInterceptors();
    this.props.signOut();
  };

  onChangeMenuIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    if (!this.props.signing) return <Redirect to="/sign" />;
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
            left: <img alt="logo" className="layout-header__logo" src={logo} />,
            right: (
              <MuiButton className="layout-header__button" size="medium" onClick={this.onClickSignOut}>
                <Text variant="body1">로그아웃</Text>
              </MuiButton>
            ),
          }}
        </AppBar>
        <Tab
          rootClassName="layout-contents"
          tabsClassName="layout-contents__menu"
          value={this.state.index}
          items={MainPage.MENU_ITEMS}
          orientation="vertical"
          onChange={this.onChangeMenuIndex}
        >
          <div className="layout-contents__item-wrapper">
            {this.state.index === 0 && <HtsConfig className="layout-contents__item-contents" />}
            {this.state.index === 1 && <Assets className="layout-contents__item-contents" />}
            {this.state.index === 2 && <Idea className="layout-contents__item-contents" />}
            {this.state.index === 3 && <CoinInfo className="layout-contents__item-contents" />}
            {this.state.index === 4 && <UseGuide className="layout-contents__item-contents" />}
            {this.state.index === 5 && <Profile className="layout-contents__item-contents" />}
          </div>
        </Tab>
      </div>
    );
  }
}

const mapStateToProps = (state: ReduxState) => ({
  signing: state.page.signing,
  token: state.page.token,
});

const mapDispatchToProps = (dispatch: Dispatch): DispatchProps => ({
  signOut: () => dispatch(pageActions.signOut()),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MainPage);
