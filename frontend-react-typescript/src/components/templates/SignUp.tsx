import * as React from 'react';
import _ from 'lodash';

import MuiButton from '@material-ui/core/Button';

import Input from 'components/atoms/Input';
import Checkbox from 'components/atoms/Checkbox';
import Text from 'components/atoms/Text';
import FormValidator from 'utils/FormValidator';

interface SignUpProps {
  // TODO: change object type to specific type
  pending: boolean;
  onSubmit: (data: object) => void;
  isDuplicatedId: (id: string) => Promise<boolean>;
}

interface SignUpState {
  errors: { [key: string]: string | undefined };
  isCheckPermitTerms: boolean;
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
    this.state = { errors: {}, isCheckPermitTerms: false };
  }

  onChangePermitTerms = () => {
    this.setState({ isCheckPermitTerms: !this.state.isCheckPermitTerms }, () => {
      this.validatePermitTerms();
    });
  };

  validateId = async () => {
    const id = this.idRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(id)) errorText = '아이디를 입력 해주세요.';
    else if (!FormValidator.validateEmail(id)) errorText = 'ID는 E-mail 형태로 입력하세요.';

    // 입력 ID form이 올바르다면, 중복 여부 제출
    if (!errorText && (await this.props.isDuplicatedId(id))) errorText = '중복된 ID 입니다.';

    if (errorText) await this.setState({ errors: { ...this.state.errors, id: errorText } });
    else await this.setState({ errors: _.omit(this.state.errors, 'id') });

    return errorText ? false : true;
  };

  validateUserName = () => {
    const name = this.nameRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(name)) errorText = '이름을 입력 해주세요.';

    this.setErrorState('userName', errorText);
    return errorText ? false : true;
  };

  validatePhoneNumber = () => {
    const phoneNumber = this.phoneNumberRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(phoneNumber)) errorText = '핸드폰 번호를 입력 해주세요.';
    else if (!FormValidator.validatePhoneNumber(phoneNumber))
      errorText = '핸드폰 번호가 올바르지 않습니다.(- 제외하고 입력하세요)';

    this.setErrorState('phoneNumber', errorText);
    return errorText ? false : true;
  };

  validatePassword = () => {
    const password = this.passwordRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(password)) errorText = '패스워드를 입력해 주세요.';
    else if (!FormValidator.validatePassword(password))
      errorText = '패스워드 패턴이 올바르지 않습니다.(영문,숫자 포함 8자 이상, 30자 이하)';

    this.setErrorState('password', errorText);
    return errorText ? false : true;
  };

  validatePasswordConfirm = () => {
    const passwordConfirm = this.passwordConfirmRef.current.value;
    let errorText = undefined;
    if (!FormValidator.isExistInput(passwordConfirm)) errorText = '패스워드를 입력해 주세요.';
    else if (passwordConfirm !== this.passwordRef.current.value)
      errorText = '패스워드 확인란이 패스워드란과 동일하지 않습니다';

    this.setErrorState('passwordConfirm', errorText);
    return errorText ? false : true;
  };

  validatePermitTerms = () => {
    const errorText = this.state.isCheckPermitTerms ? undefined : '이용약관에 동의해주세요';

    this.setErrorState('permitTerms', errorText);
    return this.state.isCheckPermitTerms;
  };

  validate = async () => {
    // ID는 중복 값 Check logic 존재 하나, 제출 직전 한번 더 검사합니다.
    return Object.keys(this.state.errors).length === 0 && (await this.validateId());
  };

  onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (await this.validate()) {
      const data = {
        email: this.idRef.current.value,
        name: this.nameRef.current.value,
        password: this.passwordRef.current.value,
      };
      this.props.onSubmit(data);
    }
  };

  render() {
    return (
      <form onSubmit={this.onSubmit}>
        <Input
          autoComplete="off"
          autoFocus
          error={this.state.errors.id ? true : false}
          helperText={this.state.errors.id}
          inputRef={this.idRef}
          name="email"
          onBlur={this.validateId}
          placeholder="E-Mail(User ID)"
        />
        <Input
          autoComplete="off"
          error={this.state.errors.userName ? true : false}
          helperText={this.state.errors.userName}
          inputRef={this.nameRef}
          name="name"
          onBlur={this.validateUserName}
          placeholder="User Name"
        />
        {/* TODO: Activate bloew after impelments phone number in backend */}
        {/* <Input
            autoComplete="off"
            error={this.state.errors.phoneNumber ? true : false}
            helperText={this.state.errors.phoneNumber}
            inputRef={this.phoneNumberRef}
            name="phoneNumber"
            onBlur={this.validatePhoneNumber}
            placeholder="Phone number('-' 없이 10자 이상 입력)"
          /> */}
        <ul>
          <li>
            <Text variant="caption" gutterBottom>
              * <em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. 회원가입 이후 <em>계정 인증용 메일</em>이
              전송됩니다.
            </Text>
          </li>
          <li>
            <Text variant="caption" gutterBottom>
              * 메일 전송은 60초 정도 소요될 수 있으며, 메일이 누락 될 경우에 <em>스팸 메일함을 확인</em> 하시기
              바랍니다.
            </Text>
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
          name="permitTerms"
          onChange={this.onChangePermitTerms}
          style={{ width: '100%' }}
          label={
            <Text variant="caption" gutterBottom>
              <em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의합니다.
            </Text>
          }
        />
        {this.state.errors.permitTerms && (
          <Text variant="caption" gutterBottom color="error">
            {this.state.errors.permitTerms}
          </Text>
        )}
        <MuiButton
          color="secondary"
          disabled={this.props.pending}
          fullWidth
          size="medium"
          type="submit"
          variant="contained"
        >
          <Text variant="button">회원가입</Text>
        </MuiButton>
      </form>
    );
  }

  private setErrorState = (field: string, errorText?: string) => {
    if (errorText) this.setState({ errors: { ...this.state.errors, [field]: errorText } });
    else this.setState({ errors: _.omit(this.state.errors, field) });
  };
}

export default SignUp;
