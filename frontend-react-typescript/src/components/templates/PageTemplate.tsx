import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';

import Paper from 'components/atoms/Paper';
import Tab from 'components/molecules/Tab';

const useStyles = makeStyles(theme => ({
  root: { display: 'flex', flexDirection: 'column' },
  tabHeader: { flex: '0 auto' },
  tabContents: { flex: '1 auto', marginTop: '6px' },
  tabContentsWrapper: { margin: '15px' },
}));

interface PageTemplateProps {
  chidren?: React.ReactNode;
  className?: string;
  index: number;
  tabHeaders: JSX.Element[];
  tabContents: JSX.Element[];
  onChangeTab: (e: React.ChangeEvent<{}>, value: any) => void;
}

const PageTemplate: React.FC<PageTemplateProps> = props => {
  const classes = useStyles();

  return (
    <div className={classNames(classes.root, props.className)}>
      <Tab
        rootClassName={classes.tabHeader}
        value={props.index}
        items={props.tabHeaders}
        orientation="horizontal"
        onChange={props.onChangeTab}
      >
        <Paper className={classes.tabContents}>
          <div className={classes.tabContentsWrapper}>{props.tabContents[props.index]}</div>
        </Paper>
      </Tab>
    </div>
  );
};

export default PageTemplate;
