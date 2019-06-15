import React from 'react';

interface Props {
  tabStyle?: string,
}

class Tab extends React.Component<Props> {
  public static defaultProps = {
    tabStyle: 's-member o-tab-menu',
  };

  render() {
    return (
      <div className={this.props.tabStyle}>
        {this.props.children}
      </div>
    );
  }
}

export default Tab;