import React from 'react';

interface TabProps {
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
  onClickTab(key: string, event:any): void,
}

const Tab: React.FunctionComponent<TabProps> = ({tabStyle, entryMode, entryModeList, navPos, onClickTab, children}) => {
  const liElement = entryModeList.map((modeElement) => {
    const {id, title, desc, link} = modeElement;
    return (
      <li
        key={id}
        className={entryMode === title ? "on" : ""}
      >
        <a href={link} onClick={(event) => onClickTab(title, event)}>
          <span>{desc}</span>
        </a>
      {/*
        <a href={"#mb-" + this.state.mode_list[i]} onClick={()=>this.handleClick(title)}>
        https://stackoverflow.com/questions/750486/javascript-closure-inside-loops-simple-practical-example
      */}
      </li>
    )
  });
  return (
    <div className={tabStyle}>
      <ul className='ui-tab-menu mb-tab'>
        {liElement}
        <span className='mb-tab-line' style={navPos}></span>
      </ul>
      {children}
    </div>
  )
};

Tab.defaultProps = {
  tabStyle: 's-member o-tab-menu',
};;

export default Tab;