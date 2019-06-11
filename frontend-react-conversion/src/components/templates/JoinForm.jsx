import React from 'react';
import InputBox from '../atoms/InputBox'
import Button from '../atoms/Button'

class JoinForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            user_name: "",
            phone_number: "",
            email_id: "",
            password: "",
            password_confirm: "",
            is_valid_password: {
                    password: false,
                    password_confirm: false
            }
        }
    }
    static defaultProps = {
        isTabOn: false,
    }


    handleInputChange(data_name, event) {
        var input_value = event.target.value;
        if (data_name === "phone_number") {
            input_value = this.autoHypenPhone(input_value);
        }
        if (data_name === "password" || data_name === "password_confirm") {
            var valid_set = this.state.is_valid_password;
            valid_set[data_name] = this.isValidPassword(input_value);
            this.setState({
                valid_set
            })
        }
        this.setState({
            [data_name]: input_value,
        });
    }

    autoHypenPhone(str){
        str = str.replace(/[^0-9]/g, '');
        var tmp = '';
        if( str.length < 4){
            return str;
        }else if(str.length < 7){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3);
            return tmp;
        }else if(str.length < 11){
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 3);
            tmp += '-';
            tmp += str.substr(6);
            return tmp;
        }else{
            tmp += str.substr(0, 3);
            tmp += '-';
            tmp += str.substr(3, 4);
            tmp += '-';
            tmp += str.substr(7);
            return tmp;
        }
    }

    isValidPassword(str){
        console.log(str);
        if (str.length > 7 && /\d/.test(str) && /[a-z]/.test(str.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    render(){
        var tabOnClassName = this.props.isTabOn ? "tab-on" : "";
        return  (
            <div id="mb-join" className={`ui-tab-cont sm-join ${tabOnClassName}`}>
                <InputBox placeholder="User name" inputClassType="inp-st2" value={this.state.user_name} changeHandler={this.handleInputChange.bind(this, "user_name")}/>
                <InputBox placeholder="Phone number" inputClassType="inp-st2" isPhone={true} value={this.state.phone_number} changeHandler={this.handleInputChange.bind(this, "phone_number")}/>
                <InputBox placeholder="Email(User ID)" inputClassType="inp-st2" value={this.state.email_id} changeHandler={this.handleInputChange.bind(this, "email_id")}/>
                <div className="a-row a-mt20">
                <ul className="btxtl-st1">
                    <li><em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. <br /> 회원가입 절차에 <em>계정 인증용 메일</em>이 전송됩니다.</li>
                    <li>메일 전송은 60초 정도 소요될 수 있으며, 메일이 <br /> <em>누락 될 경우에 스팸 메일 함을 확인</em> 하시기 바랍니다.</li>
                </ul>
                </div>
                <InputBox placeholder="Passsword(영문 숫자포함 8자 이상)" inputType="password" inputClassType="inp-st2" classType={`a-mt20 ${this.state.is_valid_password["password"] ? "" : "isalert"}`} value={this.state.password} changeHandler={this.handleInputChange.bind(this, "password")}/>
                <InputBox placeholder="Passsword confirm(영문 숫자 포함 8자 이상)" inputType="password" inputClassType="inp-st2" classType={`${this.state.is_valid_password["password_confirm"] ? "" : "isalert"}`} value={this.state.password_confirm} changeHandler={this.handleInputChange.bind(this, "password_confirm")}/>
                <div className="a-row a-mt20">
                    <label htmlFor="agree" className="chk-base chk-st1">
                    <input type="checkbox" id="agree" className="a11y"/>
                        <span className="label"><em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의합니다.</span> 
                    </label>
                </div>
                <Button classType="a-mt20" buttonType="btn-st2" buttonName="회원가입"/>
            </div>
            
        );
    }
}

export default JoinForm;