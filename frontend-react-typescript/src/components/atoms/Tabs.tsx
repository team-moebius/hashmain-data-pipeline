import React from 'react';

import MuiTabs, { TabsProps } from '@material-ui/core/Tabs';
import MuiTab, { TabProps } from '@material-ui/core/Tab';
import { withStyles } from '@material-ui/core/styles';

interface HoriztonalTabProps extends TabProps {}
interface HoriztonalTabsProps extends TabsProps {}

const HorizontalTab: React.FC<HoriztonalTabProps> = props => <MuiTab {...props} />;

const HorizontalTabs: React.FC<HoriztonalTabsProps> = props => (
  <MuiTabs {...props}>{props.children}</MuiTabs>
);

const VerticalTab = withStyles(theme => ({
  selected: {
    borderLeft: '2px solid',
  },
}))(MuiTab);

const VerticalTabs = withStyles(theme => ({
  flexContainer: {
    flexDirection: 'column',
  },
  indicator: {
    display: 'none',
  },
}))(MuiTabs);

export default { HorizontalTab, HorizontalTabs, VerticalTab, VerticalTabs };
