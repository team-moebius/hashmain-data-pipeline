import React from 'react';

interface Props {
  tabStyle?: string,
  entryMode: string,
  entryModeList: {
    id: number,
    title: string,
    desc: string,
    link: string,
  }[],
  navPos: {
    width: string,
    left: string,
  },
  onClickTab: any,
}

interface State {
  entryModeList: {
    id: number,
    title: string,
    desc: string,
    link: string,
  }[],
}


class Tab extends React.Component<Props, State> {
  constructor(props: Props) {
    super(props);
    console.log(this.props);
    this.state = {
      entryModeList: this.props.entryModeList,
    };
  }
  public static defaultProps = {
    tabStyle: 's-member o-tab-menu',
  };

  render() {
    const liElement = this.state.entryModeList.map((modeElement) => {
      return (
        <li
          key={(modeElement.id).toString()}
          className={this.props.entryMode === modeElement.title ? "on" : ""}
        >
          <a href={modeElement.link} onClick={this.props.onClickTab.bind(this, modeElement.title)}>
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
        <ul className='ui-tab-menu mb-tab'>
          {liElement}
          <span className='mb-tab-line' style={this.props.navPos}></span>
        </ul>
        {this.props.children}
      </div>
    );
  }
}

export default Tab;