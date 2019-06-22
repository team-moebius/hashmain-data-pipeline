import * as React from 'react';

import MuiButton from '@material-ui/core/Button';

import Input from 'components/atoms/Input';

interface SignInFormProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
}

const SignInForm: React.FC<SignInFormProps> = props => {
  return (
    <form onSubmit={props.onSubmit}>
      <Input placeholder="E-Mail" />
      <Input type="password" placeholder="Password" />
      <MuiButton color="secondary" fullWidth variant="contained">
        로그인
      </MuiButton>
    </form>
  );
};

export default SignInForm;
