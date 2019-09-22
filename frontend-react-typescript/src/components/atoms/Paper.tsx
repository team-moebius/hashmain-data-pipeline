import React from 'react';

import { withStyles } from '@material-ui/core/styles';
import MuiPaper, { PaperProps as MuiPaperProps } from '@material-ui/core/Paper';

const CustomPaper = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.primary.main,
  },
}))(MuiPaper);

const Paper: React.FC<MuiPaperProps> = props => <CustomPaper {...props}>{props.children}</CustomPaper>;

export default Paper;
