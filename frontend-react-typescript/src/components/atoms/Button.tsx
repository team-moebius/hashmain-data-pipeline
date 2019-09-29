import MuiButton from '@material-ui/core/Button';
import { withStyles } from '@material-ui/styles';

const Button = withStyles(theme => ({}))(MuiButton);

Button.defaultProps = {
  color: 'secondary',
  fullWidth: true,
  size: 'medium',
  type: 'button',
  variant: 'contained',
};

export default Button;
