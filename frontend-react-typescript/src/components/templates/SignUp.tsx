import * as React from 'react';
import _ from 'lodash';

import Input from 'components/atoms/Input';
import Checkbox from 'components/atoms/Checkbox';
import Text from 'components/atoms/Text';
import Button from 'components/atoms/Button';
import InputValidator from 'utils/InputValidator';

type SignUpPayloadType = 'email' | 'name' | 'password';
type SignUpValidationType = SignUpPayloadType | 'permitTerms' | 'passwordConfirm';
type SignUpPayload = { [key in SignUpPayloadType]?: string };

interface SignUpChildSlots {
  idHelp: React.ReactChild;
  end: React.ReactChild;
}

interface SignUpProps {
  children?: SignUpChildSlots;
  pending: boolean;
  onSubmit: (data: SignUpPayload) => void;
  isDuplicatedId: (id: string) => Promise<boolean>;
}

interface SignUpState {
  errors: SignUpPayload;
  isCheckPermitTerms: boolean;
}

class SignUp extends React.Component<SignUpProps, SignUpState> {
  private emailRef = React.createRef<any>();
  private nameRef = React.createRef<any>();
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

  setErrorTextState = (key: SignUpValidationType, text: string) => {
    if (InputValidator.isBlank(text)) {
      this.setState({ errors: _.omit(this.state.errors, key) });
    } else {
      this.setState({ errors: { ...this.state.errors, [key]: text } });
    }
  };

  isValidId = async () => {
    const id = this.emailRef.current.value;
    let errorText = '';

    if (InputValidator.nonValid('email', id)) {
      errorText = 'ID를 E-mail 형태로 입력해주세요.';
    } else if (await this.props.isDuplicatedId(id)) {
      errorText = '중복된 ID 입니다.';
    }

    this.setErrorTextState('email', errorText);
    return InputValidator.isBlank(id);
  };

  isValidName = () => {
    const name = this.nameRef.current.value;
    let errorText = '';
    if (InputValidator.isBlank(name)) errorText = '이름을 입력 해주세요.';

    this.setErrorState('userName', errorText);
    return errorText.length === 0;
  };

  validatePassword = () => {
    const password = this.passwordRef.current.value;
    let errorText = undefined;
    if (InputValidator.nonValid('password', password))
      errorText = '패스워드를 올바르게 입력해 주세요.(영문,숫자 포함 8자 이상, 30자 이하)';

    this.setErrorState('password', errorText);
    return errorText ? false : true;
  };

  validatePasswordConfirm = () => {
    const passwordConfirm = this.passwordConfirmRef.current.value;
    let errorText = undefined;
    if (InputValidator.isBlank(passwordConfirm)) errorText = '패스워드 확인란을 입력해 주세요.';
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
    return Object.keys(this.state.errors).length === 0 && (await this.isValidId());
  };

  onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (await this.validate()) {
      const data = {
        email: this.emailRef.current.value,
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
          autoFocus
          error={this.state.errors.id ? true : false}
          helperText={this.state.errors.id}
          inputRef={this.emailRef}
          onBlur={this.isValidId}
          placeholder="E-Mail(User ID)"
        />
        {this.props.children && this.props.children.idHelp}
        <Input
          error={this.state.errors.userName ? true : false}
          helperText={this.state.errors.userName}
          inputRef={this.nameRef}
          onBlur={this.isValidName}
          placeholder="User Name"
        />
        <Input
          error={this.state.errors.password ? true : false}
          helperText={this.state.errors.password}
          inputRef={this.passwordRef}
          onBlur={this.validatePassword}
          type="password"
          placeholder="Password(영문 숫자포함 8자 이상)"
        />
        <Input
          error={this.state.errors.passwordConfirm ? true : false}
          helperText={this.state.errors.passwordConfirm}
          inputRef={this.passwordConfirmRef}
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
        <Button disabled={this.props.pending}>
          <Text variant="button">회원가입</Text>
        </Button>
        {this.props.children && this.props.children.end}
      </form>
    );
  }
}

export default SignUp;
