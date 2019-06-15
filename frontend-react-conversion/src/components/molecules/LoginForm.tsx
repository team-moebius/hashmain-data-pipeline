import React from 'react';
import InputBox from '../atoms/InputBox'
import Button from '../atoms/Button'

interface Props {
  isTabOn: boolean,
}

interface State {
  emailId: string,
  password: string,
}

class LoginForm extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      emailId: "",
      password: "",
    };
  }

  static defaultProps = {
    isTabOn: false,
  };

  handleInputChange(dataName: string, event: any) {
    this.setState({
      [dataName]: event.target.value,
    } as Pick<State, keyof State>);
  }

  render() {
    const tabOnClassName = this.props.isTabOn ? "tab-on" : "";
    return (
      <div id="mb-login" className={`ui-tab-cont sm-login ${tabOnClassName}`}>
        <p className="txt1">
          로그인 후 CRYPYO BOX 의 서비스 이용 시 <em>이용약관</em> 및<br/><em>개인 정보 정책</em>에 동의하는 것으로 간주합니다.
        </p>
        {/* 오류 케이스 1 */}
        {/* <div className="isalert-p">
            <span className="input-base inp-st2-alert">이메일 또는 패스워드 입력 오류입니다.</span>
        </div> */}
        <p className="txt2">
          <span>or</span>
        </p>
        <InputBox
          placeholder="E-Mail"
          value={this.state.emailId}
          changeHandler={this.handleInputChange.bind(this, "emailId")}
        />
        <InputBox
          placeholder="Password"
          value={this.state.password}
          changeHandler={this.handleInputChange.bind(this, "password")}
          inputType="password"
        />
        <div className="a-row a-mt20">
          <ul className="btxtl-st1">
            <li>
              CRYPTO BOX 는 <em>모든 브라우저에 최적화</em> 되었습니다.
            </li>
          </ul>
        </div>
        <Button>로그인</Button>
        <div className="a-row">
          <label htmlFor="idsave" className="chk-base chk-st1">
            <input type="checkbox" id="idsave" className="a11y"/>
            <span className="label">아이디 저장하기</span>
          </label>
        </div>
        <div className="a-row btn-base btn-st3">
          <a href="password-reset.html">비밀번호 찾기</a>
        </div>
      </div>
    );
  }
}

export default LoginForm;