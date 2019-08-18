import React from 'react';

import MuiTypography from '@material-ui/core/Typography';

import Tabs from 'components/atoms/Tabs';

interface VerticalTabsProps {
  className: string;
  items: string[];
  index: number;
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const VerticalTabs: React.FC<VerticalTabsProps> = props => (
  <Tabs.VerticalTabs
    className={props.className}
    indicatorColor="secondary"
    textColor="secondary"
    value={props.index}
    onChange={props.onChange}
  >
    {props.items.map((item, index) => (
      <Tabs.Tab key={index} label={<MuiTypography variant="h6">{item}</MuiTypography>} />
    ))}
  </Tabs.VerticalTabs>
);

export default VerticalTabs;
