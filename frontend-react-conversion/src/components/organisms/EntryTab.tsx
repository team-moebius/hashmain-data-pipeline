import React from 'react';
import LoginForm from '../molecules/LoginForm';
import JoinForm from '../molecules/JoinForm';
import Tab from "../atoms/Tab";

interface EntryTabState {
  entryMode: string,
  navPos: {
    width: string,
    left: string,
  },
}

class EntryTab extends React.Component<{}, EntryTabState> {
  constructor(props: {}) {
    super(props);
    this.state = {
      entryMode: 'login',
      navPos: {
        width: '',
        left: '',
      },
    };
    this.onClickTabChange = this.onClickTabChange.bind(this);
  }

  onClickTabChange(key: string, event: any) {
    const child_span = event.currentTarget.childNodes[0];
    this.setState({
      navPos: {
        width: child_span.offsetWidth,
        left: child_span.offsetLeft
      },
      entryMode: key,
    });
  }

  render() {
    const data = {
      modeList: [
        {id: 0, title: "login", desc: "로그인", link: '#mb-login'},
        {id: 1, title: "join", desc: "회원가입", link: '#mb-join'},
      ],
      tab: {
        width: '',
        left: '',
      }
    };
    const {entryMode, navPos} = this.state;
    return (
      <Tab entryMode={entryMode} entryModeList={data.modeList} onClickTab={this.onClickTabChange} navPos={navPos}>
        <LoginForm isTabOn={this.state.entryMode === "login"}/>
        <JoinForm isTabOn={this.state.entryMode === "join"}/>
      </Tab>
    );
  }
}

export default EntryTab;