import React from 'react';

import MuiTypography from '@material-ui/core/Typography';

import Tabs from 'components/molecules/Tabs';

interface MenuProps {
  className: string;
  items: string[];
  index: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const Menu: React.FC<MenuProps> = props => (
  <Tabs.VerticalTabs
    className={props.className}
    indicatorColor="secondary"
    textColor="secondary"
    value={props.index}
    onChange={props.onChange}
  >
    {props.items.map((item, index) => (
      <Tabs.VerticalTab key={index} label={<MuiTypography variant="h6">{item}</MuiTypography>} />
    ))}
  </Tabs.VerticalTabs>
);

export default Menu;
