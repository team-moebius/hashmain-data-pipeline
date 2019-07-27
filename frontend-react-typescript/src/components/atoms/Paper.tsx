import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';
import MuiPaper, { PaperProps as MuiPaperProps } from '@material-ui/core/Paper';

interface PaperProps extends MuiPaperProps {}

const useStyles = makeStyles(theme => ({
  root: { backgroundColor: theme.palette.primary.main },
}));

const Paper: React.FC<PaperProps> = props => {
  const { className, ...rest } = props;
  const classes = useStyles();
  return <MuiPaper className={classNames(classes.root, className)} {...rest} />;
};

export default Paper;
