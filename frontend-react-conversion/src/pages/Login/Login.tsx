import React from 'react';
import "../../asset/css/common.css"
import "../../asset/css/member.css"
import LoginForm from '../../components/templates/LoginForm';
import JoinForm from '../../components/templates/JoinForm';

interface State {
  mode: string,
  mode_list: {
  id: number,
  title: string,
  desc: string,
}[],
  tab: {
    width: string,
    left: string,
  },
}

class Login extends React.Component<{}, State> {
  constructor(props: {}) {
    super(props);
    this.state = {
      mode: 'login',
      mode_list: [
        {id:0, title: "login", desc: "로그인"},
        {id:1, title: "join", desc: "회원가입"}
      ],
      tab: {
        width: "",
        left: ""
      },
    };

  }

  handleTabClick(key: string, event: any) {
    const child_span = event.currentTarget.childNodes[0];
    this.setState({
      mode : key,
      tab : {
        width: child_span.offsetWidth,
        left: child_span.offsetLeft
      }
    });
  }
  tabRender() {
    let lis = []
    for (let i = 0; i < this.state.mode_list.length; i++) {
      let title = this.state.mode_list[i].title;
      let desc =  this.state.mode_list[i].desc;
      lis.push(
        <li key={(i).toString()} className={this.state.mode === title ? "on" : ""}>
          <a href={"#mb-" + this.state.mode_list[i].title} onClick={this.handleTabClick.bind(this, title)}>
            <span>{desc}</span>
          </a>
            {/*
            <a href={"#mb-" + this.state.mode_list[i]} onClick={()=>this.handleClick(title)}>
            https://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
            */}
        </li>
      )
    }
    const tab_style = {
      width: this.state.tab.width.toString() + 'px',
      left: this.state.tab.left.toString() + 'px'
    }
    lis.push(
      <span key="tab_line" className="mb-tab-line" style={tab_style}/>
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