import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';

import Toolbar from 'components/atoms/Toolbar';

const useStyles = makeStyles(theme => ({
  root: {
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(1),
  },
  title: {
    flex: '0 0 auto',
  },
  spacer: {
    flex: '1 1 100%',
  },
  actions: {
    color: theme.palette.text.secondary,
  },
}));

export interface TableToolbarProps {
  action?: boolean;
  actions?: React.ReactNode;
  actionTitle?: React.ReactNode;
  className?: string;
  mainTitle: React.ReactNode;
}

const TableToolbar: React.FC<TableToolbarProps> = props => {
  const classes = useStyles();

  return (
    <Toolbar className={classNames(classes.root, props.className)}>
      <div className={classes.title}>{props.action ? props.mainTitle : props.actionTitle}</div>
      <div className={classes.spacer} />
      <div className={classes.actions}>{props.action && props.actions}</div>
    </Toolbar>
  );
};

export default TableToolbar;
