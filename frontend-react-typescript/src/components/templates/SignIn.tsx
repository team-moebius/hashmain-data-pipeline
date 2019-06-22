import * as React from 'react';

import MuiTypography from '@material-ui/core/Typography';
import MuiButton from '@material-ui/core/Button';

import Checkbox from 'components/atoms/Checkbox';
import SignInForm from 'components/organisms/SignInForm';

import 'assets/scss/templates/SignIn.scss';

interface SignInProps {
  onSubmit?: (e: React.FormEvent<HTMLFormElement>) => void;
}

const SignIn: React.FunctionComponent<SignInProps> = props => {
  return (
    <div className="sign-in__wrapper">
      <SignInForm onSubmit={props.onSubmit} />
      <Checkbox label="아이디 저장하기" />
      <div>
        <ul className="sign-in__caption">
          <li>
            <MuiTypography variant="body2" gutterBottom>
              * 로그인 후 CRYPYO BOX 의 서비스 이용 시 <em>이용약관</em> 및 <em>개인 정보 정책</em>
              에 동의하는 것으로 간주합니다.
            </MuiTypography>
          </li>
          <li>
            <MuiTypography variant="body2" gutterBottom>
              * CRYPTO BOX 는 <em>모든 브라우저에 최적화</em> 되었습니다.
            </MuiTypography>
          </li>
        </ul>
      </div>
      <MuiButton fullWidth>비밀번호 찾기</MuiButton>
    </div>
  );
};

export default SignIn;
