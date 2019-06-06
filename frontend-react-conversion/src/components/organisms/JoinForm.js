import React from 'react';
import InputBox from '../atoms/InputBox'

class JoinForm extends React.Component {
    static defaultProps = {
        isTabOn: false,
    }
    render(){
        var tabOnClassName = this.props.isTabOn ? "tab-on" : "";
        return  (
            <div id="mb-join" class={`ui-tab-cont sm-join ${tabOnClassName}`}>
                This is join form.
                <InputBox placeholder="User name"/>
                <InputBox placeholder="Phone number"/>
                <InputBox placeholder="Email"/>
                <div class="a-row a-mt20">
                <ul class="btxtl-st1">
                    <li><em>수신이 가능한 이메일 주소</em>를 입력하시기 바랍니다. <br /> 회원가입 절차에 <em>계정 인증용 메일</em>이 전송됩니다.</li>
                    <li>메일 전송은 60초 정도 소요될 수 있으며, 메일이 <br /> <em>누락 될 경우에 스팸 메일 함을 확인</em> 하시기 바랍니다.</li>
                </ul>
              </div>
            </div>
        );
    }
}

export default JoinForm;