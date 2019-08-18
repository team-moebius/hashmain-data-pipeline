import React from 'react';
import classNames from 'classnames';

import MuiTabs, { TabsProps as MuiTabsProps } from '@material-ui/core/Tabs';
import MuiTab, { TabProps as MuiTabProps } from '@material-ui/core/Tab';
import { makeStyles } from '@material-ui/core/styles';

interface TabProps extends MuiTabProps {}
interface TabsProps extends MuiTabsProps {}

const Tab: React.FC<TabProps> = props => <MuiTab {...props} />;

const HorizontalTabs: React.FC<TabsProps> = props => (
  <MuiTabs {...props} orientation="horizontal">
    {props.children}
  </MuiTabs>
);

const useVerticalTabsStyles = makeStyles(theme => ({
  tabs: { borderLeft: theme.palette.primary.main },
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

const Tabs: React.FC<TabsProps> = props => {
  return props.orientation === 'horizontal' ? (
    <HorizontalTabs {...props}>{props.children}</HorizontalTabs>
  ) : (
    <VerticalTabs {...props}>{props.children}</VerticalTabs>
  );
};

Tabs.defaultProps = {
  indicatorColor: 'secondary',
  textColor: 'secondary',
};

export default { Tab, Tabs };
