import * as React from 'react';
import { Redirect } from 'react-router-dom';
import { withAlert, AlertManager } from 'react-alert';

import MuiTypography from '@material-ui/core/Typography';

import Paper from 'components/atoms/Paper';
import Tabs from 'components/atoms/Tabs';
import SignIn from 'components/templates/SignIn';
import SignUp from 'components/templates/SignUp';
import Ajax from 'utils/Ajax';

import 'assets/scss/SignPage.scss';

interface SignPageProps {
  alert: AlertManager;
}

interface SignPageState {
  index: 0 | 1;
  signing: boolean;
}

//TODO: Connect with backend
class SignPage extends React.Component<SignPageProps, SignPageState> {
  constructor(props: SignPageProps) {
    super(props);
    this.state = { index: 0, signing: false };
  }

  isDuplicatedId = (id: string) => {
    return false;
  };

  onSubmitSignIn = (data: object) => {
    Ajax.post('/member/', data)
      .then(response => {
        this.setState({ signing: true });
        this.props.alert.success('로그인 되었습니다.');
      })
      .catch(error => {
        this.props.alert.error('로그인에 실패하였습니다.');
      });
  };

  onSubmitSignUp = (data: object) => {
    Ajax.post('/member/signup', data)
      .then(() => {
        this.setState({ index: 0 });
        this.props.alert.success('회원 가입에 성공하였습니다.');
      })
      .catch(error => {
        this.props.alert.error('회원 가입에 실패하였습니다. ' + error);
      });
  };

  onChangeTabs = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    if (this.state.signing) return <Redirect to="/" />;
    return (
      <Paper className="sign-page">
        <Paper className="sign-page__wrapper" square>
          <Tabs.HorizontalTabs
            centered
            indicatorColor="secondary"
            textColor="secondary"
            value={this.state.index}
            onChange={this.onChangeTabs}
          >
            <Tabs.HorizontalTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  로그인
                </MuiTypography>
              }
            />
            <Tabs.HorizontalTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  회원가입
                </MuiTypography>
              }
            />
          </Tabs.HorizontalTabs>
          <div className="sign-page__contents">
            {this.state.index === 0 && <SignIn onSubmit={this.onSubmitSignIn} />}
            {this.state.index === 1 && (
              <SignUp isDuplicatedId={this.isDuplicatedId} onSubmit={this.onSubmitSignUp} />
            )}
          </div>
        </Paper>
      </Paper>
    );
  }
}

export default withAlert()(SignPage);
