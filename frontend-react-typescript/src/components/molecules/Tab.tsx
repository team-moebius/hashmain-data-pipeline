import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';

import TabHeader from 'components/atoms/TabHeader';
import TabHeaderItem from 'components/atoms/TabHeaderItem';

interface TabProps {
  centered?: boolean;
  chidren?: React.ReactNode;
  items: JSX.Element[];
  rootClassName?: string;
  tabsClassName?: string;
  value: number;
  orientation: 'horizontal' | 'vertical';
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const useHorizontalStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    width: '100%',
  },
  tabs: { backgroundColor: theme.palette.background.paper },
  tab: {},
}));

const useVerticalStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
    display: 'flex',
  },
  tabs: {
    backgroundColor: theme.palette.background.paper,
  },
  tab: { minWidth: '100%' },
}));

const Tab: React.FC<TabProps> = props => {
  const verticalClasses = useVerticalStyles();
  const horizontalClasses = useHorizontalStyles();
  const classes = props.orientation === 'vertical' ? verticalClasses : horizontalClasses;
  const rootClassName = classNames(props.rootClassName, classes.root);
  const tabsClassName = classNames(props.tabsClassName, classes.tabs);

  return (
    <div className={rootClassName}>
      <TabHeader
        centered={props.centered}
        className={tabsClassName}
        orientation={props.orientation}
        value={props.value}
        onChange={props.onChange}
      >
        {props.items.map((item, index) => (
          <TabHeaderItem className={classes.tab} key={index} label={item} />
        ))}
      </TabHeader>
      {props.children}
    </div>
  );
};

export default Tab;
