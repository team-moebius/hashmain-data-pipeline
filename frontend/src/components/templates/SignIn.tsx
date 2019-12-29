import * as React from 'react';

import Input from 'components/atoms/Input';
import Text from 'components/atoms/Text';
import Button from 'components/atoms/Button';
import InputValidator from 'utils/InputValidator';

type SignInPayloadType = 'email' | 'password';
type SignInPayload = { [key in SignInPayloadType]?: string };

interface SignInProps {
  pending?: boolean;
  onSubmit: (data: SignInPayload) => void;
}

interface SignInState {
  errors: SignInPayload;
}

class SignIn extends React.Component<SignInProps, SignInState> {
  private emailRef = React.createRef<any>();
  private passwordRef = React.createRef<any>();

  public constructor(props: SignInProps) {
    super(props);
    this.state = { errors: {} };
  }

  private onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (this.isValid()) {
      this.props.onSubmit({ email: this.emailRef.current.value, password: this.passwordRef.current.value });
    }
  };

  private isValid = () => {
    let idErrorText = '';
    let passwordErrorText = '';

    if (InputValidator.nonValid('email', this.emailRef.current.value)) {
      idErrorText = 'ID를 Email 형태로 입력해주세요.';
    }
    if (InputValidator.isBlank(this.passwordRef.current.value)) {
      passwordErrorText = '패스워드를 입력 해주세요.';
    }

    this.setState({ errors: { email: idErrorText, password: passwordErrorText } });

    return InputValidator.isBlank(idErrorText) && InputValidator.isBlank(passwordErrorText);
  };

  public render() {
    return (
      <form onSubmit={this.onSubmit}>
        <Input
          autoComplete="off"
          autoFocus
          error={!!this.state.errors.email}
          helperText={this.state.errors.email}
          inputRef={this.emailRef}
          placeholder="E-Mail"
          style={{ marginBottom: '8px' }}
        />
        <Input
          error={!!this.state.errors.password}
          helperText={this.state.errors.password}
          inputRef={this.passwordRef}
          type="password"
          placeholder="Password"
          style={{ marginBottom: '8px' }}
        />
        <Button color="secondary" disabled={this.props.pending} type="submit" style={{ marginTop: '8px' }}>
          <Text variant="button">로그인</Text>
        </Button>
        {this.props.children}
      </form>
    );
  }
}

export default SignIn;
