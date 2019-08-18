import React from 'react';

import Tabs from 'components/atoms/Tabs';

interface BasicTabsProps {
  centered?: boolean;
  className?: string;
  items: JSX.Element[];
  value: number;
  orientation: 'horizontal' | 'vertical';
  onChange: (e: React.ChangeEvent<{}>, value: any) => void;
}

const BasicTabs: React.FC<BasicTabsProps> = props => (
  <Tabs.Tabs indicatorColor="secondary" textColor="secondary" {...props}>
    {props.items.map((item, index) => (
      <Tabs.Tab key={index} label={item} />
    ))}
  </Tabs.Tabs>
);

export default BasicTabs;
