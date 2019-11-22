import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { withAlert, AlertManager } from 'react-alert';

import ApiKeyRegistIcon from '@material-ui/icons/VpnKey';
import AccountIcon from '@material-ui/icons/AccountCircle';

import IconButton from 'components/atoms/IconButton';
import Dialog from 'components/atoms/Dialog';
import Paper from 'components/atoms/Paper';
import AppBar from 'components/molecules/AppBar';
import Tab from 'components/molecules/Tab';
import ApiKeyRegistBox from 'components/organisms/ApiKeyRegistBox';
import HtsConfig from 'pages/menu/HtsConfig';
import Assets from 'pages/menu/Assets';
import Idea from 'pages/menu/Idea';
import CoinInfo from 'pages/menu/CoinInfo';
import UseGuide from 'pages/menu/UseGuide';
import Profile from 'pages/menu/Profile';
import { actionCreators as pageActions } from 'pages/PageWidgets';
import ajax, { addSignOutInterceptor, addJwtTokenInterceptor, ejectInterceptors } from 'utils/Ajax';
import { ReduxState } from 'infra/redux/GlobalState';

import HtsIcon from 'assets/icons/HtsIcon';
import bgImage from 'assets/images/bg.png';
// import logo from 'assets/images/logo.svg';
import 'assets/scss/MainPage.scss';

interface StateProps {
  signing: boolean;
  token: string;
}

interface DispatchProps {
  signOut: () => void;
}

interface MainPageProps extends StateProps, DispatchProps {
  alert: AlertManager;
}

interface MainPageState {
  apiKeyRegistDialogOpen: boolean;
  index: number;
}

class MainPage extends React.Component<MainPageProps, MainPageState> {
  static readonly MENU_ITEMS: JSX.Element[] = [
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      HTS 설정
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      CTS 설정
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      TDS 설정
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      자산관리
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      아이디어
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      이용안내
    </div>,
    <div className="layout-menu__item">
      <HtsIcon className="layout-menu__icon" />
      프로필
    </div>,
  ];

  constructor(props: MainPageProps) {
    super(props);
    this.state = {
      apiKeyRegistDialogOpen: false,
      index: 0,
    };

    addSignOutInterceptor(this.props.signOut);
    addJwtTokenInterceptor(this.props.token);
  }

  onClickApiKeyRegistButton = (e: React.MouseEvent<HTMLElement>) => {
    this.setState({ ...this.state, apiKeyRegistDialogOpen: true });
  };

  onCloseApiKeyRegist = (e: React.MouseEvent<HTMLElement>) => {
    this.setState({ ...this.state, apiKeyRegistDialogOpen: false });
  };

  onClickSignOut = () => {
    ejectInterceptors();
    this.props.signOut();
  };

  onChangeMenuIndex = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  // TODO: 어차피 Biz component 인 ApiKeyRegistBox로 해당 event 전부 옮기고 props로 api만 주입하는 식으로처리
  // 위처럼 해야 로딩처리까지 깔끔해짐
  onSubmitApiRegist = (data: object) => {
    ajax
      .post('/api/api-keys', data)
      .then(response => {
        this.props.alert.success('등록 성공');
      })
      .catch(error => {
        this.props.alert.error('등록 실패');
      });
  };

  onClickViewMyApiKeyButton = () => {
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
    if (!this.props.signing) return <Redirect to="/sign" />;
    const { index } = this.state;

    return (
      <div style={{ backgroundImage: bgImage }} className="layout">
        <AppBar className="layout-header" position="absolute" title="CRYPTO BOX GLOBAL.">
          {{
            left: <img alt="logo" className="layout-header__logo" />,
            right: (
              <div className="layout-header__icons">
                <IconButton icon={<ApiKeyRegistIcon />} onClick={this.onClickApiKeyRegistButton} />
                <IconButton icon={<AccountIcon />} onClick={this.onClickSignOut} />
              </div>
            ),
          }}
        </AppBar>
        <Tab
          rootClassName="layout-contents"
          tabsClassName="layout-contents__menu"
          tabHeaderItemClassName="layout-tab__item"
          value={index}
          items={MainPage.MENU_ITEMS}
          orientation="vertical"
          onChange={this.onChangeMenuIndex}
        >
          <div className="layout-contents__item-wrapper">
            {index === 0 && <HtsConfig className="layout-contents__item-contents" />}
            {index === 1 && <Assets className="layout-contents__item-contents" />}
            {index === 2 && <Idea className="layout-contents__item-contents" />}
            {index === 3 && <CoinInfo className="layout-contents__item-contents" />}
            {index === 4 && <UseGuide className="layout-contents__item-contents" />}
            {index === 5 && <Profile className="layout-contents__item-contents" />}
          </div>
        </Tab>
        <Paper className="layout-footer">Footer</Paper>
        <Dialog open={this.state.apiKeyRegistDialogOpen} onClose={this.onCloseApiKeyRegist}>
          <ApiKeyRegistBox
            className="layout-dialog__api-regist"
            onSubmit={this.onSubmitApiRegist}
            onClickViewMyApiKeyButton={this.onClickViewMyApiKeyButton}
          />
        </Dialog>
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
)(withAlert<MainPageProps>()(MainPage));
