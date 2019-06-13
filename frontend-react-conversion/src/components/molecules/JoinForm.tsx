import React from 'react';
import InputBox from '../atoms/InputBox'
import Button from '../atoms/Button'

interface Props {
  isTabOn: boolean,
}

interface State {
  user_name: string,
  phone_number: string,
  email_id: string,
  password: string,
  password_confirm: string,
  is_valid_password: {
    password: boolean,
    password_confirm: boolean,
  },
}

class JoinForm extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      user_name: '',
      phone_number: '',
      email_id: '',
      password: '',
      password_confirm: '',
      is_valid_password: {
        password: false,
        password_confirm: false,
      }
    };
  }

  public static defaultProps = {
    isTabOn: false,
  };

  handleInputChange(data_name: string, event: any) {
    let input_value = event.target.value;
    if (data_name === "phone_number") {
      input_value = this.autoHypenPhone(input_value);
    }
    if (data_name === "password" || data_name === "password_confirm") {
      let valid_set = this.state.is_valid_password;
      valid_set[data_name] = this.isValidPassword(input_value);
      this.setState({
        is_valid_password: valid_set,
      })
    }
    this.setState({
      [data_name]: input_value,
    } as Pick<State, keyof State>);
  }

  autoHypenPhone(str: string) {
    let input_str = str.replace(/[^0-9]/g, '');
    if (input_str.length < 4) {
      return input_str;
    } else if (str.length < 7) {
      return `${input_str.substr(0, 3)}-${input_str.substr(3)}`;
    } else if (str.length < 11) {
      return `${input_str.substr(0, 3)}-${input_str.substr(3, 3)}-${input_str.substr(6)}`;
    } else {
      return `${input_str.substr(0, 3)}-${input_str.substr(3, 4)}-${input_str.substr(7)}`;
    }
  }

  isValidPassword(str: string) {
    return (str.length > 7 && /\d/.test(str) && /[a-z]/.test(str.toLowerCase()));
  }

  render() {
    let tabOnClassName = this.props.isTabOn ? "tab-on" : "";
    return (
      <div id="mb-join" className={`ui-tab-cont sm-join ${tabOnClassName}`}>
        <InputBox
          placeholder="User name"
          value={this.state.user_name}
          changeHandler={this.handleInputChange.bind(this, "user_name")}
        />
        <InputBox
          placeholder="Phone number"
          value={this.state.phone_number}
          isPhone={true}
          changeHandler={this.handleInputChange.bind(this, "phone_number")}
        />
        <InputBox
          placeholder="Email(User ID)"
          value={this.state.email_id}
          changeHandler={this.handleInputChange.bind(this, "email_id")}
        />
        <div className="a-row a-mt20">
          <ul className="btxtl-st1">
            <li><em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. <br/> 회원가입 절차에 <em>계정 인증용 메일</em>이 전송됩니다.</li>
            <li>메일 전송은 60초 정도 소요될 수 있으며, 메일이 <br/> <em>누락 될 경우에 스팸 메일 함을 확인</em> 하시기 바랍니다.</li>
          </ul>
        </div>
        <InputBox
          placeholder="Passsword(영문 숫자포함 8자 이상)"
          inputType="password"
          className="a-mt20"
          isAlert={!this.state.is_valid_password["password"]}
          value={this.state.password}
          changeHandler={this.handleInputChange.bind(this, "password")}
        />
        <InputBox
          placeholder="Passsword confirm(영문 숫자 포함 8자 이상)"
          inputType="password"
          isAlert={!this.state.is_valid_password["password_confirm"]}
          value={this.state.password_confirm}
          changeHandler={this.handleInputChange.bind(this, "password_confirm")}
        />
        <div className="a-row a-mt20">
          <label htmlFor="agree" className="chk-base chk-st1">
            <input type="checkbox" id="agree" className="a11y"/>
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