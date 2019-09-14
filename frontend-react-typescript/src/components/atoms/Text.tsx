import { withStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

const Text = withStyles(theme => ({
  root: {
    color: theme.palette.primary.contrastText,
  },
}))(Typography);

export default Text;
