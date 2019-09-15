import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import MuiPaper, { PaperProps as MuiPaperProps } from '@material-ui/core/Paper';

const MoebiusPaper = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.primary.main,
  },
}))(MuiPaper);

const Paper: React.FC<MuiPaperProps> = props => <MoebiusPaper {...props}>{props.children}</MoebiusPaper>;

export default Paper;
