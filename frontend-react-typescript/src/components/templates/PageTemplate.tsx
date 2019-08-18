import * as React from 'react';
import BasicTabs from 'components/molecules/BasicTabs';

interface PageTemplateProps {
  index: number;
  tabHeaders: JSX.Element[];
  tabContents: JSX.Element[];
  onChangeTab: (e: React.ChangeEvent<{}>, value: any) => void;
}

const PageTemplate: React.FC<PageTemplateProps> = props => {
  return (
    <div>
      <BasicTabs
        value={props.index}
        items={props.tabHeaders}
        orientation="horizontal"
        onChange={props.onChangeTab}
      />
      <div>{props.tabContents[props.index]}</div>
    </div>
  );
};

export default PageTemplate;
