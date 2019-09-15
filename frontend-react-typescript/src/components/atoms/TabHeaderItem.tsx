import * as React from 'react';
import MuiTab, { TabProps as MuiTabProps } from '@material-ui/core/Tab';

export interface TabHeaderItemProps extends MuiTabProps {}

const TabHeaderItem: React.FC<TabHeaderItemProps> = props => <MuiTab {...props}>{props.children}</MuiTab>;

TabHeaderItem.defaultProps = {
  disableRipple: true,
};

export default TabHeaderItem;
