import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';

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
import { actionCreators as pageActions } from 'pages/PageWidgets';
import ajax, { addSignOutInterceptor, addJwtTokenInterceptor, ejectInterceptors } from 'utils/Ajax';
import { ReduxState } from 'utils/GlobalReducer';

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
            leftSide: <img alt="logo" className="layout-header__logo" src={logo} />,
            rightSide: (
              <>
                <MuiButton
                  className="layout-header__button"
                  size="medium"
                  onClick={this.onClickAlertSample}
                >
                  <MuiTypography variant="h6">얼럿 샘플</MuiTypography>
                </MuiButton>
                <MuiButton
                  className="layout-header__button"
                  size="medium"
                  onClick={this.onClickSignOut}
                >
                  <MuiTypography variant="h6">로그아웃</MuiTypography>
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
