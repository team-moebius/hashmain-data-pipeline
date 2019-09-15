import * as React from 'react';
import MuiTabs, { TabsProps as MuiTabsProps } from '@material-ui/core/Tabs';

export interface TabHeaderProps extends MuiTabsProps {}

const TabHeader: React.FC<TabHeaderProps> = props => <MuiTabs {...props}>{props.children}</MuiTabs>;

TabHeader.defaultProps = {
  variant: 'scrollable',
  indicatorColor: 'secondary',
  textColor: 'secondary',
};

export default TabHeader;
