import MuiButton from '@material-ui/core/Button';
import { withStyles } from '@material-ui/styles';

const Button = withStyles(theme => ({
  root: {
    '&:hover': {
      backgroundColor: theme.palette.secondary.dark,
    },
  },
}))(MuiButton);

Button.defaultProps = {
  color: 'secondary',
  fullWidth: true,
  size: 'medium',
  type: 'button',
  variant: 'contained',
};

export default Button;
