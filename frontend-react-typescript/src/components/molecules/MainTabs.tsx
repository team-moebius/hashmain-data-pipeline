import * as React from 'react';

import { makeStyles } from '@material-ui/core/styles';
import MuiTabs from '@material-ui/core/Tabs';
import MuiTab from '@material-ui/core/Tab';

interface MainTabsProps {
  className: string;
  items: JSX.Element[];
  value: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const useStyles = makeStyles(theme => ({
  tabs: {},
  tab: { minWidth: '100%' },
}));

const MainTabs: React.FC<MainTabsProps> = props => {
  const classes = useStyles();
  return (
    <MuiTabs className={classes.tabs} orientation="vertical" {...props}>
      {props.items.map((item, index) => (
        <MuiTab className={classes.tab} key={index} label={item} />
      ))}
    </MuiTabs>
  );
};

export default MainTabs;
