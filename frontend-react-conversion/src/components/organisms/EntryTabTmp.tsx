import React from 'react';
import LoginForm from '../molecules/LoginForm';
import JoinForm from '../molecules/JoinForm';
import TabTmp from "../atoms/TabTmp";

interface State {
  mode: string,
  modeList: {
    id: number,
    title: string,
    desc: string,
  }[],
  tab: {
    width: string,
    left: string,
  },
}

class EntryTabTmp extends React.Component<{}, State> {
  constructor(props: {}) {
    super(props);
  }

  handleTabClick(key: string, event: any) {
    const child_span = event.currentTarget.childNodes[0];
    this.setState({
      mode: key,
      tab: {
        width: child_span.offsetWidth,
        left: child_span.offsetLeft
      }
    });
  }

  tabRender() {
    const li_element = this.state.modeList.map((modeElement) => {
      const id = modeElement.id;
      const title = modeElement.title;
      const desc = modeElement.desc;
      return (<li key={(id).toString()} className={this.state.mode === title ? "on" : ""}>
        <a href={"#mb-" + title} onClick={this.handleTabClick.bind(this, title)}>
          <span>{desc}</span>
        </a>
        {/*
          <a href={"#mb-" + this.state.mode_list[i]} onClick={()=>this.handleClick(title)}>
          https://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
        */}
      </li>)
    });

    const tabStyle = {
      width: this.state.tab.width.toString() + 'px',
      left: this.state.tab.left.toString() + 'px'
    };



    return (
      <ul className="ui-tab-menu mb-tab">
        {li_element}
        <span key="tab_line" className="mb-tab-line" style={tabStyle}/>
      </ul>
    );
  }

  render() {
    const tabProps = {
      mode: 'login',
      modeList: [
        {id: 0, title: "login", desc: "로그인", link: '#mb-login'},
        {id: 1, title: "join", desc: "회원가입", link: '#mb-join'},
      ],
    }
    return (
      <TabTmp defaultMode={tabProps.mode} modeList={tabProps.modeList}>
        {this.tabRender()}
        <LoginForm isTabOn={this.state.mode === "login"}/>
        <JoinForm isTabOn={this.state.mode === "join"}/>
      </TabTmp>
    );
  }
}

export default EntryTabTmp;