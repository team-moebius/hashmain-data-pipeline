import React from 'react';

import MuiTypography from '@material-ui/core/Typography';

import Tabs from 'components/atoms/Tabs';

interface MenuProps {
  className: string;
  items: string[];
  index: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const HorizontalTabs: React.FC<MenuProps> = props => (
  <Tabs.HorizontalTabs
    className={props.className}
    indicatorColor="secondary"
    textColor="secondary"
    value={props.index}
    onChange={props.onChange}
  >
    {props.items.map((item, index) => (
      <Tabs.HorizontalTab key={index} label={<MuiTypography variant="h6">{item}</MuiTypography>} />
    ))}
  </Tabs.HorizontalTabs>
);

export default HorizontalTabs;
