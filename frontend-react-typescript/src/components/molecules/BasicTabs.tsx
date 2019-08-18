import React from 'react';

import { withStyles } from '@material-ui/core/styles';

import Tabs from 'components/atoms/Tabs';

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
}))(Tabs.Tabs);

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
}))(Tabs.Tab);

interface BasicTabsProps {
  centered?: boolean;
  className?: string;
  items: string[];
  value: number;
  orientation: 'horizontal' | 'vertical';
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const BasicTabs: React.FC<BasicTabsProps> = props => (
  <StyledTabs indicatorColor="secondary" textColor="secondary" {...props}>
    {props.items.map((item, index) => (
      <StyledTab key={index} label={item} />
    ))}
  </StyledTabs>
);

export default BasicTabs;
