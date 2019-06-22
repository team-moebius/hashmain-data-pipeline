import * as React from 'react';

import Tab from 'components/atoms/Tab';
import LoginForm from 'components/molecules/LoginForm';
// import JoinForm from 'components/molecules/JoinForm';
import SignInForm from 'components/organisms/SignInForm';

interface EntryTabState {
  entryMode: string;
  navPos: {
    width: string;
    left: string;
  };
}

class EntryTab extends React.Component<{}, EntryTabState> {
  public constructor(props: {}) {
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

  public onClickTabChange(key: string, event: any) {
    const childSpan = event.currentTarget.childNodes[0];
    this.setState({
      navPos: {
        width: childSpan.offsetWidth,
        left: childSpan.offsetLeft,
      },
      entryMode: key,
    });
  }

  public render() {
    const data = {
      modeList: [
        { id: 0, title: 'login', desc: '로그인', link: '#mb-login' },
        { id: 1, title: 'join', desc: '회원가입', link: '#mb-join' },
      ],
      tab: {
        width: '',
        left: '',
      },
    };
    const { entryMode, navPos } = this.state;
    return (
      <Tab
        entryMode={entryMode}
        entryModeList={data.modeList}
        onClickTab={this.onClickTabChange}
        navPos={navPos}
      >
        <LoginForm isTabOn={this.state.entryMode === 'login'} />
        <SignInForm />
      </Tab>
    );
  }
}

export default EntryTab;
