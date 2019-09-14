import { withStyles } from '@material-ui/core/styles';
import MuiPaper from '@material-ui/core/Paper';

const Paper = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.primary.main,
  },
}))(MuiPaper);

export default Paper;
