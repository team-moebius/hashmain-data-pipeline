import * as React from 'react';

import MuiTypography from '@material-ui/core/Typography';
import MuiButton from '@material-ui/core/Button';

import Input from 'components/atoms/Input';
import Checkbox from 'components/atoms/Checkbox';
import FormValidator from 'utils/FormValidator';

interface SignInProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
}

interface SignInState {
  errors: { [key: string]: string | undefined };
}

class SignIn extends React.Component<SignInProps, SignInState> {
  private idRef = React.createRef<any>();
  private passwordRef = React.createRef<any>();
  constructor(props: SignInProps) {
    super(props);
    this.state = { errors: {} };
  }

  validate = (id: string, password: string) => {
    let idErrorText = undefined;
    let passwordErrorText = undefined;
    // Check required value of form
    if (!FormValidator.isExistInput(id)) idErrorText = '아이디를 입력 해주세요.';
    else if (!FormValidator.validateEmail(id)) idErrorText = 'ID는 E-mail 형태로 입력하세요.';

    if (!FormValidator.isExistInput(password)) passwordErrorText = '패스워드를 입력 해주세요.';

    this.setState({ errors: { id: idErrorText, password: passwordErrorText } });

    return idErrorText && passwordErrorText ? false : true;
  };

  onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (
      this.validate(this.idRef.current.value, this.passwordRef.current.value) &&
      this.props.onSubmit
    ) {
      this.props.onSubmit(e);
    }
  };

  render() {
    return (
      <>
        <form onSubmit={this.onSubmit}>
          <Input
            error={this.state.errors.id ? true : false}
            helperText={this.state.errors.id}
            inputRef={this.idRef}
            name="id"
            placeholder="E-Mail"
          />
          <Input
            error={this.state.errors.password ? true : false}
            helperText={this.state.errors.password}
            inputRef={this.passwordRef}
            name="password"
            type="password"
            placeholder="Password"
          />
          <MuiButton color="secondary" fullWidth size="large" type="submit" variant="contained">
            <MuiTypography variant="h5">로그인</MuiTypography>
          </MuiButton>
        </form>
        <Checkbox label="아이디 저장하기" />
        <ul>
          <li style={{ marginTop: '10px' }}>
            <MuiTypography variant="body1" gutterBottom>
              * 로그인 후 CRYPYO BOX 의 서비스 이용 시 <em>이용약관</em> 및 <em>개인 정보 정책</em>
              에 동의하는 것으로 간주합니다.
            </MuiTypography>
          </li>
          <li>
            <MuiTypography variant="body1" gutterBottom>
              * CRYPTO BOX 는 <em>모든 브라우저에 최적화</em> 되었습니다.
            </MuiTypography>
          </li>
        </ul>
        <MuiButton fullWidth>비밀번호 찾기</MuiButton>
      </>
    );
  }
}

export default SignIn;
