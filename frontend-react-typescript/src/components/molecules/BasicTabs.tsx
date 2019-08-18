import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import MuiTabs from '@material-ui/core/Tabs';
import MuiTab from '@material-ui/core/Tab';

const StyledTabs = withStyles(theme => ({
  root: {
    minHeight: 45,
    paddingLeft: '20px',
  },
  indicator: {
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: 'transparent',
    '& > div': {
      maxWidth: 100,
      height: 1,
      width: '100%',
      backgroundColor: theme.palette.secondary.main,
    },
  },
}))(MuiTabs);

const StyledTab = withStyles(theme => ({
  root: {
    minWidth: 120,
    minHeight: 45,
    padding: 0,
    textTransform: 'none',
    fontWeight: theme.typography.fontWeightRegular,
    fontSize: theme.typography.pxToRem(16),
    '&:focus': {
      opacity: 1,
    },
  },
}))(MuiTab);

interface BasicTabsProps {
  centered?: boolean;
  className?: string;
  items: string[];
  value: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const BasicTabs: React.FC<BasicTabsProps> = props => (
  <StyledTabs
    {...props}
    indicatorColor="secondary"
    textColor="secondary"
    TabIndicatorProps={{ children: <div /> }}
  >
    {props.items.map((item, index) => (
      <StyledTab disableRipple key={index} label={item} />
    ))}
  </StyledTabs>
);

export default BasicTabs;
