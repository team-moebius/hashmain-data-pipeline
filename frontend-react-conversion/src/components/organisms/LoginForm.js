import React from 'react';
import InputBox from '../atoms/InputBox'

class LoginForm extends React.Component {
    static defaultProps = {
        isTabOn: false,
    }
    render(){
        var tabOnClassName = this.props.isTabOn ? "tab-on" : "";
        return  (
            <div id="mb-login" class={`ui-tab-cont sm-login ${tabOnClassName}`}>
                This is login form.
                <InputBox placeholder="Email"/>
                <InputBox placeholder="Password"/>
                <div class="a-row a-mt20">
						<ul class="btxtl-st1">
							<li>CRYPTO BOX 는 <em>모든 브라우저에 최적화</em> 되었습니다.</li>
						</ul>
                </div>
            </div>
        );
    }
}

export default LoginForm;