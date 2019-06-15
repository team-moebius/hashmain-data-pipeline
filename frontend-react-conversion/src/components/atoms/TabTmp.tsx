import React from 'react';

interface Props {
  tabStyle?: string,
  defaultMode: string,
  modeList: {
    id: number,
    title: string,
    desc: string,
    link: string,
  }[],
}

interface State {
  mode: string,
  modeList: {
    id: number,
    title: string,
    desc: string,
    link: string,
  }[],
  tab: {
    width: string,
    left: string,
  },
}


class TabTmp extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    this.state = {
      mode: this.props.defaultMode,
      modeList: this.props.modeList,
      tab: {
        width: '',
        left: '',
      },
    }
  }
  public static defaultProps = {
    tabStyle: 's-member o-tab-menu',
  };

  onClickTabChange(key: string, event: any) {
    const child_span = event.currentTarget.childNodes[0];
    this.setState({
      mode: key,
      tab: {
        width: child_span.offsetWidth,
        left: child_span.offsetLeft
      }
    });
  }

  render() {
    const liElement = this.state.modeList.map((modeElement) => {
      return (
        <li
          key={(modeElement.id).toString()}
          className={this.state.mode === modeElement.title ? "on" : ""}
        >
          <a href={modeElement.link} onClick={this.onClickTabChange.bind(this, modeElement.title)}>
            <span>{modeElement.desc}</span>
          </a>
        {/*
          <a href={"#mb-" + this.state.mode_list[i]} onClick={()=>this.handleClick(title)}>
          https://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
        */}
        </li>
      )
    });
    return (
      <div className={this.props.tabStyle}>
        {this.props.children}
      </div>
    );
  }
}

export default TabTmp;