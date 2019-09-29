import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { withAlert, AlertManager } from 'react-alert';

import Paper from 'components/atoms/Paper';
import CircularLoader from 'components/molecules/CircularLoader';
import BasicTabs from 'components/molecules/BasicTabs';
import SignIn from 'components/templates/SignIn';
import SignUp from 'components/templates/SignUp';
import { actionCreators as pageActions } from 'pages/PageWidgets';
import ajax from 'utils/Ajax';
import { ReduxState } from 'infra/redux/GlobalState';

import 'assets/scss/SignPage.scss';
import Text from 'components/atoms/Text';

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
  static readonly MENU_ITEMS: JSX.Element[] = [<>로그인</>, <>회원가입</>];

  constructor(props: SignPageProps) {
    super(props);
    this.state = { index: 0, pending: false };
  }

  isDuplicatedId = async (id: string) => {
    let isDuplicatedId = false;
    await this.setState({ pending: true });
    await ajax
      .get(`/api/members/duplicate/${id}`)
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
        .post('/api/members', data)
        .then(response => {
          this.setState({ pending: false }, () => this.props.signInSuccess(response.data.token));
        })
        .catch(error => {
          this.setState({ pending: false });
          if (error.response && error.response.status === 401) {
            this.props.alert.error('이메일 인증을 완료하세요.');
          } else {
            this.props.alert.error('로그인 실패. ID/Password를 확인하세요.');
          }
        });
    });
  };

  onSubmitSignUp = (data: object) => {
    this.setState({ pending: true }, () => {
      ajax
        .post('/api/members/signup', data)
        .then(() => {
          this.setState({ index: 0, pending: false });
          this.props.alert.success('가입 성공. 인증 메일을 확인하세요.');
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
        {this.state.pending && <CircularLoader />}
        <Paper className="sign-page__wrapper" square>
          <BasicTabs
            centered
            items={SignPage.MENU_ITEMS}
            value={this.state.index}
            orientation="horizontal"
            onChange={this.onChangeTabs}
          />
          <div className="sign-page__contents">
            {this.state.index === 0 && (
              <SignIn pending={this.state.pending} onSubmit={this.onSubmitSignIn}>
                <ul>
                  <li>
                    <Text gutterBottom variant="caption">
                      로그인 후 CRYPYO BOX 의 서비스 이용 시 <em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의하는
                      것으로 간주합니다.
                    </Text>
                  </li>
                  <li>
                    <Text gutterBottom variant="caption">
                      CRYPTO BOX 는 <em>모든 브라우저에 최적화</em> 되었습니다.
                    </Text>
                  </li>
                </ul>
              </SignIn>
            )}
            {this.state.index === 1 && (
              <SignUp isDuplicatedId={this.isDuplicatedId} pending={this.state.pending} onSubmit={this.onSubmitSignUp}>
                {{
                  idHelp: (
                    <ul>
                      <li>
                        <Text variant="caption" gutterBottom>
                          <em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. 회원가입 이후{' '}
                          <em>계정 인증용 메일</em>이 전송됩니다.
                        </Text>
                      </li>
                      <li>
                        <Text variant="caption" gutterBottom>
                          메일 전송은 60초 정도 소요될 수 있으며, 메일이 누락 될 경우에 <em>스팸 메일함을 확인</em>{' '}
                          하시기 바랍니다.
                        </Text>
                      </li>
                    </ul>
                  ),
                  end: '',
                }}
              </SignUp>
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
