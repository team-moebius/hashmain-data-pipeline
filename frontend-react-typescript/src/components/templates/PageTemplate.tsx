import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';

import Paper from 'components/atoms/Paper';
import BasicTabs from 'components/molecules/BasicTabs';

const useStyles = makeStyles(theme => ({
  root: { display: 'flex', flexDirection: 'column' },
  tabHeader: { flex: '0 auto' },
  tabContents: { flex: '1 auto', marginTop: 4 },
}));

interface PageTemplateProps {
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
      <Paper className={classes.tabHeader}>
        <BasicTabs value={props.index} items={props.tabHeaders} orientation="horizontal" onChange={props.onChangeTab} />
      </Paper>
      <Paper className={classes.tabContents}>
        <div style={{ marginTop: '4px' }}>{props.tabContents[props.index]}</div>
      </Paper>
    </div>
  );
};

export default PageTemplate;
