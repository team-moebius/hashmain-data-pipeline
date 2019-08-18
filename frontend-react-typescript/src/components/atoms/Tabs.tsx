import React from 'react';
import classNames from 'classnames';

import MuiTabs, { TabsProps as MuiTabsProps } from '@material-ui/core/Tabs';
import MuiTab, { TabProps as MuiTabProps } from '@material-ui/core/Tab';
import { makeStyles } from '@material-ui/core/styles';

import { Omit } from 'utils/Omit';

interface TabProps extends MuiTabProps {}
interface TabsProps extends Omit<MuiTabsProps, 'orientation'> {}

const Tab: React.FC<TabProps> = props => <MuiTab {...props} />;

const HorizontalTabs: React.FC<TabsProps> = props => (
  <MuiTabs {...props} orientation="horizontal">
    {props.children}
  </MuiTabs>
);

const useVerticalTabsStyles = makeStyles(theme => ({
  tabs: { borderRight: theme.palette.primary.main },
}));

const VerticalTabs: React.FC<TabsProps> = props => {
  const classes = useVerticalTabsStyles();
  const { className, ...rest } = props;
  return (
    <MuiTabs className={classNames(classes.tabs, className)} {...rest} orientation="vertical">
      {props.children}
    </MuiTabs>
  );
};

export default { Tab, HorizontalTabs, VerticalTabs };
