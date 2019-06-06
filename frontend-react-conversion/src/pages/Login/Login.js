import React from 'react';
import LoginForm from '../../components/organisms/LoginForm';
import JoinForm from '../../components/organisms/JoinForm';
import "../../asset/css/common.css"
import "../../asset/css/member.css"


class Login extends React.Component {
    constructor() {
        super();
        this.state = {
            // 0 to login, 1 to join
            clicked : "#mb-login",
            href_list : {"#mb-login" : "로그인", "#mb-join" : "회원가입"}
        };
    }
    handleClick(key) {
        // e.preventDefault;
        this.setState({
            clicked : key
        });
        console.log("state changed to ", key);
    }
    mbtab() {
        var lis = []
        for (var key in this.state.href_list) {
            var isOn  = "";
            if (this.state.clicked === key) {
                isOn = "on"
            }
            lis.push(<li class={isOn}><a href={key} onClick={this.handleClick(key).bind(this)}><span>{this.state.href_list[key]}</span></a></li>)
        }
        return (
            <ul class="ui-tab-menu mb-tab">
                {lis}
            </ul>
        );
    }
    render(){
        return  (
            <div class="inner-member">
                <div class="ly-member">
                    <div class="o-tab-menu">
                        {this.mbtab()}
                        <LoginForm isTabOn={true}/>
                        <JoinForm />
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;