import * as React from 'react';
import { Redirect } from 'react-router-dom';

import MuiTabs from '@material-ui/core/Tabs';
import MuiTab from '@material-ui/core/Tab';
import MuiTypography from '@material-ui/core/Typography';

import Paper from 'components/atoms/Paper';
import SignIn from 'components/templates/SignIn';
import SignUp from 'components/templates/SignUp';

import 'assets/scss/pages/SignPage.scss';

interface SignPageProps {}
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

  onSubmitSignIn = (e: React.FormEvent<HTMLFormElement>) => {
    // Temporary code
    this.setState({ signing: true });
  };

  onSubmitSignUp = (e: React.FormEvent<HTMLFormElement>) => {};

  onChangeTabs = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    if (this.state.signing) return <Redirect to="/" />;
    return (
      <Paper className="sign-page">
        <Paper className="sign-page__wrapper" square>
          <MuiTabs
            centered
            indicatorColor="secondary"
            textColor="secondary"
            value={this.state.index}
            onChange={this.onChangeTabs}
          >
            <MuiTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  로그인
                </MuiTypography>
              }
            />
            <MuiTab
              label={
                <MuiTypography variant="h6" gutterBottom>
                  회원가입
                </MuiTypography>
              }
            />
          </MuiTabs>
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

export default SignPage;
