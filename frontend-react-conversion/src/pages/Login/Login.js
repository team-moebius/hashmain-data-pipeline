import React from 'react';
// import ReactDOM from 'react-dom';
import "../../asset/css/common.css"
import "../../asset/css/member.css"
import LoginForm from '../../components/organisms/LoginForm';
import JoinForm from '../../components/organisms/JoinForm';
// import TabBar from './TabBar';


class Login extends React.Component {
    constructor() {
        super();
        this.state = {
            mode: 'login',
            mode_list: [
                {id:0, title: "login", desc: "로그인"},
                {id:1, title: "join", desc: "회원가입"}
            ],
            tab: {
                width: "",
                left: ""
            }
        };

    }

    handleTabClick(key, event) {
        var child_span = event.currentTarget.childNodes[0];
        this.setState({
            mode : key,
            tab : {
                width: child_span.offsetWidth,
                left: child_span.offsetLeft
            }
        });
        console.log(event.currentTarget);
    }

    tabRender() {
        const lis = []

        for (let i = 0; i < this.state.mode_list.length; i++) {
            let title = this.state.mode_list[i].title;
            let desc =  this.state.mode_list[i].desc;
            lis.push(
                <li key={(i).toString()} className={this.state.mode === title ? "on" : ""}>
                    <a href={"#mb-" + this.state.mode_list[i].title} ref={this.state.mode_list[i].pos} onClick={this.handleTabClick.bind(this, title)}>
                        <span>{desc}</span>
                    </a>
                    {/*
                    <a href={"#mb-" + this.state.mode_list[i]} onClick={()=>this.handleClick(title)}>
                    https://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
                    */}
                </li>
            )
        }
        var tab_style = {
            width: this.state.tab.width.toString() + 'px',
            left: this.state.tab.left.toString() + 'px'
        }
        lis.push(
            <span key="tab_line" className="mb-tab-line" style={tab_style}></span>
        )
        return (
            <ul className="ui-tab-menu mb-tab">
                {lis}
            </ul>
        );
    }

    render(){
        return  (
            <div className="inner-member">
                <div className="ly-member">
                    <div className="s-member o-tab-menu">
                        {this.tabRender()}
                        <LoginForm isTabOn={this.state.mode === "login"}/>
                        <JoinForm isTabOn={this.state.mode === "join"}/>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login;