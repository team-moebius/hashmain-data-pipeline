import MuiCircularProgress from '@material-ui/core/CircularProgress';
import { withStyles } from '@material-ui/styles';

const MoebCircularProgress = withStyles(theme => ({}))(MuiCircularProgress);

MoebCircularProgress.defaultProps = {
  color: 'secondary',
  disableShrink: true,
};

export default MoebCircularProgress;
