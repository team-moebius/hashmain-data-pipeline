import React from 'react';
import InputBox from '../atoms/InputBox'
import Button from '../atoms/Button'

class JoinForm extends React.Component {
    static defaultProps = {
        isTabOn: false,
    }
    render(){
        var tabOnClassName = this.props.isTabOn ? "tab-on" : "";
        return  (
            <div id="mb-join" className={`ui-tab-cont sm-join ${tabOnClassName}`}>
                <InputBox placeholder="User name" inputClassType="inp-st2"/>
                <InputBox placeholder="Phone number" inputClassType="inp-st2" isPhone={true}/>
                <InputBox placeholder="Email" inputClassType="inp-st2"/>
                <div className="a-row a-mt20">
                <ul className="btxtl-st1">
                    <li><em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. <br /> 회원가입 절차에 <em>계정 인증용 메일</em>이 전송됩니다.</li>
                    <li>메일 전송은 60초 정도 소요될 수 있으며, 메일이 <br /> <em>누락 될 경우에 스팸 메일 함을 확인</em> 하시기 바랍니다.</li>
                </ul>
                </div>
                <InputBox placeholder="Passsword(영문 숫자포함 8자 이상)" inputType="password" inputClassType="inp-st2" classType="a-mt20 isalert"/>
                <InputBox placeholder="Passsword confirm(영문 숫자 포함 8자 이상)" inputType="password" inputClassType="inp-st2" classType="isalert"/>
                <div class="a-row a-mt20">
                    <label for="agree" class="chk-base chk-st1">
                    <input type="checkbox" id="agree" class="a11y"/>
                        <span class="label"><em>이용약관</em> 및 <em>개인 정보 정책</em>에 동의합니다.</span> 
                    </label>
                </div>
                <Button classType="a-mt20" buttonType="btn-st2" buttonName="회원가입"/>
            </div>
            
        );
    }
}

export default JoinForm;