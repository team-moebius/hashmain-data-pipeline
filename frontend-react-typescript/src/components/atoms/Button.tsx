import MuiButton from '@material-ui/core/Button';
import { withStyles } from '@material-ui/styles';

const MoebButton = withStyles(theme => ({}))(MuiButton);

MoebButton.defaultProps = {
  color: 'secondary',
  fullWidth: true,
  size: 'large',
  type: 'submit',
  variant: 'contained',
};

export default MoebButton;
