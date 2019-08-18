import * as React from 'react';
import { makeStyles } from '@material-ui/core/styles';

import Tabs from 'components/atoms/Tabs';

interface MainTabsProps {
  className: string;
  items: JSX.Element[];
  value: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const useTabStyles = makeStyles(theme => ({
  mainTab: { minWidth: '100%' },
}));

const MainTabs: React.FC<MainTabsProps> = props => {
  const classes = useTabStyles();
  return (
    <Tabs.Tabs orientation="vertical" {...props}>
      {props.items.map((item, index) => (
        <Tabs.Tab className={classes.mainTab} key={index} label={item} />
      ))}
    </Tabs.Tabs>
  );
};

export default MainTabs;
