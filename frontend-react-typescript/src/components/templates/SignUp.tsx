import * as React from 'react';

import MuiTypography from '@material-ui/core/Typography';
import MuiButton from '@material-ui/core/Button';

import Input from 'components/atoms/Input';
import Checkbox from 'components/atoms/Checkbox';
import FormValidator from 'utils/FormValidator';

interface SignUpProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
  isDuplicatedId?: (id: string) => boolean;
}

interface SignUpState {
  errors: { [key: string]: string | undefined };
}

// TODO: Refactoring input validation code
// Add 'division' prop to molecules/Input, Move validating logic
// Or, split EmailInput, PasswordInput.etc
class SignUp extends React.Component<SignUpProps, SignUpState> {
  private idRef = React.createRef<any>();
  private nameRef = React.createRef<any>();
  private phoneNumberRef = React.createRef<any>();
  private passwordRef = React.createRef<any>();
  private passwordConfirmRef = React.createRef<any>();

  constructor(props: SignUpProps) {
    super(props);
    this.state = { errors: {} };
  }

  validateId = () => {
    const id = this.idRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(id)) errorText = '아이디를 입력 해주세요.';
    else if (!FormValidator.validateEmail(id)) errorText = 'ID는 E-mail 형태로 입력하세요.';
    else if (this.props.isDuplicatedId && this.props.isDuplicatedId(id))
      errorText = '중복된 ID입니다.';

    this.setState({ errors: { ...this.state.errors, id: errorText } });
    return errorText ? false : true;
  };

  validateUserName = () => {
    const name = this.nameRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(name)) errorText = '이름을 입력 해주세요.';

    this.setState({ errors: { ...this.state.errors, userName: errorText } });
    return errorText ? false : true;
  };

  validatePhoneNumber = () => {
    const phoneNumber = this.phoneNumberRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(phoneNumber)) errorText = '핸드폰 번호를 입력 해주세요.';
    else if (!FormValidator.validatePhoneNumber(phoneNumber))
      errorText = '핸드폰 번호가 올바르지 않습니다.(- 제외하고 입력하세요)';

    this.setState({ errors: { ...this.state.errors, phoneNumber: errorText } });
    return errorText ? false : true;
  };

  validatePassword = () => {
    const password = this.passwordRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(password)) errorText = '패스워드를 입력해 주세요.';
    else if (!FormValidator.validatePassword(password))
      errorText = '패스워드 패턴이 올바르지 않습니다.(영문,숫자 포함 8자 이상, 30자 이하)';

    this.setState({ errors: { ...this.state.errors, password: errorText } });
    return errorText ? false : true;
  };

  validatePasswordConfirm = () => {
    const passwordConfirm = this.passwordConfirmRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(passwordConfirm)) errorText = '패스워드를 입력해 주세요.';
    else if (passwordConfirm !== this.passwordRef.current.value)
      errorText = '패스워드 확인란이 패스워드란과 동일하지 않습니다';

    this.setState({ errors: { ...this.state.errors, passwordConfirm: errorText } });
    return errorText ? false : true;
  };

  validate = async () => {
    let valid = false;
    valid = await this.validateId();
    valid = await this.validatePassword();
    valid = await this.validatePasswordConfirm();
    valid = await this.validatePhoneNumber();
    valid = await this.validateUserName();

    return valid;
  };

  onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (this.validate() && this.props.onSubmit) this.props.onSubmit(e);
  };

  render() {
    return (
      <>
        <form onSubmit={this.onSubmit}>
          <Input
            autoComplete="off"
            error={this.state.errors.id ? true : false}
            helperText={this.state.errors.id}
            inputRef={this.idRef}
            name="id"
            onBlur={this.validateId}
            placeholder="E-Mail(User ID)"
          />
          <Input
            autoComplete="off"
            error={this.state.errors.userName ? true : false}
            helperText={this.state.errors.userName}
            inputRef={this.nameRef}
            name="userName"
            onBlur={this.validateUserName}
            placeholder="User Name"
          />
          <Input
            autoComplete="off"
            error={this.state.errors.phoneNumber ? true : false}
            helperText={this.state.errors.phoneNumber}
            inputRef={this.phoneNumberRef}
            name="phoneNumber"
            onBlur={this.validatePhoneNumber}
            placeholder="Phone number('-' 없이 10자 이상 입력)"
          />
          <ul>
            <li style={{ marginTop: '10px' }}>
              <MuiTypography variant="body1" gutterBottom>
                * <em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. 회원가입 이후{' '}
                <em>계정 인증용 메일</em>이 전송됩니다.
              </MuiTypography>
            </li>
            <li>
              <MuiTypography variant="body1" gutterBottom>
                * 메일 전송은 60초 정도 소요될 수 있으며, 메일이 누락 될 경우에{' '}
                <em>스팸 메일함을 확인</em> 하시기 바랍니다.
              </MuiTypography>
            </li>
          </ul>
          <Input
            error={this.state.errors.password ? true : false}
            helperText={this.state.errors.password}
            inputRef={this.passwordRef}
            name="password"
            onBlur={this.validatePassword}
            type="password"
            placeholder="Password(영문 숫자포함 8자 이상)"
          />
          <Input
            error={this.state.errors.passwordConfirm ? true : false}
            helperText={this.state.errors.passwordConfirm}
            inputRef={this.passwordConfirmRef}
            name="passwordConfirm"
            onBlur={this.validatePasswordConfirm}
            type="password"
            placeholder="Password confirm(영문 숫자포함 8자 이상)"
          />
          <Checkbox
            label={
              <MuiTypography variant="body1" gutterBottom>
                <em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의합니다.
              </MuiTypography>
            }
          />
          <MuiButton color="secondary" fullWidth size="large" type="submit" variant="contained">
            <MuiTypography variant="h5">회원가입</MuiTypography>
          </MuiButton>
        </form>
      </>
    );
  }
}

export default SignUp;
