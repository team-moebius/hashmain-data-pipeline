import * as React from 'react';

import InputBox from 'components/atoms/InputBox';
import Button from 'components/atoms/Button';

interface JoinFormProps {
  isTabOn: boolean;
}

interface JoinFormState {
  userName: string;
  phoneNumber: string;
  emailId: string;
  password: string;
  passwordConfirm: string;
  isValidPassword: {
    password: boolean;
    passwordConfirm: boolean;
  };
}

class JoinForm extends React.Component<JoinFormProps, JoinFormState> {
  public constructor(props: JoinFormProps) {
    super(props);
    this.state = {
      userName: '',
      phoneNumber: '',
      emailId: '',
      password: '',
      passwordConfirm: '',
      isValidPassword: {
        password: false,
        passwordConfirm: false,
      },
    };
  }

  public static defaultProps = {
    isTabOn: false,
  };

  public handleInputChange(dataName: string, event: any) {
    let inputValue = event.target.value;
    if (dataName === 'phoneNumber') {
      inputValue = this.autoHypenPhone(inputValue);
    }
    if (dataName === 'password' || dataName === 'passwordConfirm') {
      let validSet = this.state.isValidPassword;
      validSet[dataName] = this.isValidPassword(inputValue);
      this.setState({
        isValidPassword: validSet,
      });
    }
    this.setState({
      [dataName]: inputValue,
    } as Pick<JoinFormState, keyof JoinFormState>);
  }

  public autoHypenPhone(str: string) {
    let inputStr = str.replace(/[^0-9]/g, '');
    if (inputStr.length < 4) {
      return inputStr;
    } else if (str.length < 7) {
      return `${inputStr.substr(0, 3)}-${inputStr.substr(3)}`;
    } else if (str.length < 11) {
      return `${inputStr.substr(0, 3)}-${inputStr.substr(3, 3)}-${inputStr.substr(6)}`;
    } else {
      return `${inputStr.substr(0, 3)}-${inputStr.substr(3, 4)}-${inputStr.substr(7)}`;
    }
  }

  public isValidPassword(str: string) {
    return str.length > 7 && /\d/.test(str) && /[a-z]/.test(str.toLowerCase());
  }

  public render() {
    let tabOnClassName = this.props.isTabOn ? 'tab-on' : '';
    return (
      <div id="mb-join" className={`ui-tab-cont sm-join ${tabOnClassName}`}>
        <InputBox
          placeholder="User name"
          value={this.state.userName}
          changeHandler={this.handleInputChange.bind(this, 'userName')}
        />
        <InputBox
          placeholder="Phone number"
          value={this.state.phoneNumber}
          isPhone={true}
          changeHandler={this.handleInputChange.bind(this, 'phoneNumber')}
        />
        <InputBox
          placeholder="Email(User ID)"
          value={this.state.emailId}
          changeHandler={this.handleInputChange.bind(this, 'emailId')}
        />
        <div className="a-row a-mt20">
          <ul className="btxtl-st1">
            <li>
              <em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. <br /> 회원가입 절차에{' '}
              <em>계정 인증용 메일</em>이 전송됩니다.
            </li>
            <li>
              메일 전송은 60초 정도 소요될 수 있으며, 메일이 <br />{' '}
              <em>누락 될 경우에 스팸 메일 함을 확인</em> 하시기 바랍니다.
            </li>
          </ul>
        </div>
        <InputBox
          placeholder="Passsword(영문 숫자포함 8자 이상)"
          inputType="password"
          className="a-mt20"
          isAlert={!this.state.isValidPassword['password']}
          value={this.state.password}
          changeHandler={this.handleInputChange.bind(this, 'password')}
        />
        <InputBox
          placeholder="Passsword confirm(영문 숫자 포함 8자 이상)"
          inputType="password"
          isAlert={!this.state.isValidPassword['passwordConfirm']}
          value={this.state.passwordConfirm}
          changeHandler={this.handleInputChange.bind(this, 'passwordConfirm')}
        />
        <div className="a-row a-mt20">
          <label htmlFor="agree" className="chk-base chk-st1">
            <input type="checkbox" id="agree" className="a11y" />
            <span className="label">
              <em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의합니다.
            </span>
          </label>
        </div>
        <Button>회원가입</Button>
      </div>
    );
  }
}

export default JoinForm;
