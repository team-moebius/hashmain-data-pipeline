import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { withAlert, AlertManager } from 'react-alert';

import MuiTypography from '@material-ui/core/Typography';

import Paper from 'components/atoms/Paper';
import Tabs from 'components/atoms/Tabs';
import SignIn from 'components/templates/SignIn';
import SignUp from 'components/templates/SignUp';
import { actionCreators as pageActions } from 'pages/PageWidgets';
import ajax from 'utils/Ajax';
import { ReduxState } from 'utils/GlobalReducer';

import 'assets/scss/SignPage.scss';

interface StateProps {
  signing: boolean;
}

interface DispatchProps {
  signInSuccess: (token: string) => void;
}

interface SignPageProps extends StateProps, DispatchProps {
  alert: AlertManager;
}

interface SignPageState {
  index: 0 | 1;
  pending: boolean;
}

class SignPage extends React.Component<SignPageProps, SignPageState> {
  constructor(props: SignPageProps) {
    super(props);
    this.state = { index: 0, pending: false };
  }

  isDuplicatedId = async (id: string) => {
    let isDuplicatedId = false;
    await this.setState({ pending: true });
    await ajax
      .get(`/members/duplicate/${id}`)
      .then(() => {
        isDuplicatedId = true;
      })
      .catch(() => {
        isDuplicatedId = false;
      });
    await this.setState({ pending: false });

    return isDuplicatedId;
  };

  onSubmitSignIn = (data: object) => {
    this.setState({ pending: true }, () => {
      ajax
        .post('/members', data)
        .then(response => {
          this.setState({ pending: false }, () => this.props.signInSuccess(response.data.token));
        })
        .catch(error => {
          this.setState({ pending: false });
          if (error.response && error.response.status === 401) {
            this.props.alert.error('해당 계정은 이메일 인증이 완료되지 않았습니다.');
          } else {
            this.props.alert.error('로그인 실패. ID 혹은 Password를 확인하세요.');
          }
        });
    });
  };

  onSubmitSignUp = (data: object) => {
    this.setState({ pending: true }, () => {
      ajax
        .post('/members/signup', data)
        .then(() => {
          this.setState({ index: 0, pending: false });
          this.props.alert.success('회원 가입 성공. 인증 메일을 보냈으니 확인하세요.');
        })
        .catch(error => {
          this.setState({ pending: false });
          this.props.alert.error('회원 가입 실패');
        });
    });
  };

  onChangeTabs = (e: React.ChangeEvent<{}>, value: any) => {
    this.setState({ index: value });
  };

  render() {
    if (this.props.signing) return <Redirect to="/" />;
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
            {this.state.index === 0 && (
              <SignIn pending={this.state.pending} onSubmit={this.onSubmitSignIn} />
            )}
            {this.state.index === 1 && (
              <SignUp
                isDuplicatedId={this.isDuplicatedId}
                pending={this.state.pending}
                onSubmit={this.onSubmitSignUp}
              />
            )}
          </div>
        </Paper>
      </Paper>
    );
  }
}

const mapStateToProps = (state: ReduxState) => ({
  signing: state.page.signing,
});

const mapDispatchToProps = (dispatch: Dispatch): DispatchProps => ({
  signInSuccess: (token: string) => dispatch(pageActions.signInSuccess({ token })),
});

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withAlert<SignPageProps>()(SignPage));
