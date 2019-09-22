import MuiTextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';

const Input = withStyles(theme => ({
  root: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        borderColor: theme.palette.primary.main,
      },
    },
    backgroundColor: theme.palette.primary.light,
    borderRadius: 4,
    margin: '8px 0px 0px 0',
  },
}))(MuiTextField);

Input.defaultProps = {
  autoComplete: 'off',
  fullWidth: true,
  margin: 'dense',
  variant: 'outlined',
  inputProps: { style: { padding: '11px' } },
};

export default Input;
