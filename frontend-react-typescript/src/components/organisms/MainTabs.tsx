import * as React from 'react';

import { withStyles } from '@material-ui/core/styles';

import Tabs from 'components/atoms/Tabs';
import BasicTabs from 'components/molecules/BasicTabs';

const MainTab = withStyles(theme => ({
  root: {
    [theme.breakpoints.up('md')]: {
      maxWidth: '100px',
    },
  },
}))(Tabs.Tab);

interface MainTabsProps {
  className: string;
  items: JSX.Element[];
  value: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const MainTabs: React.FC<MainTabsProps> = props => {
  return (
    <BasicTabs orientation="vertical" {...props}>
      {props.items.map((item, index) => (
        <MainTab key={index} label={item} />
      ))}
    </BasicTabs>
  );
};

export default MainTabs;
