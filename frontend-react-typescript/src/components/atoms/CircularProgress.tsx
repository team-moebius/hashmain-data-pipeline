import MuiCircularProgress from '@material-ui/core/CircularProgress';
import { withStyles } from '@material-ui/styles';

const CircularProgress = withStyles(theme => ({}))(MuiCircularProgress);

CircularProgress.defaultProps = {
  color: 'secondary',
  disableShrink: true,
};

export default CircularProgress;
