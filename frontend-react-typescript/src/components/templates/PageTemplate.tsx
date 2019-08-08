import * as React from 'react';
import HorizontalTabs from 'components/molecules/HorizontalTabs';

interface PageTemplateProps {
  index: number;
  tabLabels: string[];
  tabContents: JSX.Element[];
  onChangeTab: (e: React.ChangeEvent<{}>, value: any) => void;
}

const PageTemplate: React.FC<PageTemplateProps> = props => {
  return (
    <div>
      <HorizontalTabs
        className="layout-contents__menu"
        index={props.index}
        items={props.tabLabels}
        onChange={props.onChangeTab}
      />
      <div>{props.tabContents[props.index]}</div>
    </div>
  );
};

export default PageTemplate;
