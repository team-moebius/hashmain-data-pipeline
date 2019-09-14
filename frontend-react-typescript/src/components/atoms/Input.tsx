import { withStyles } from '@material-ui/core/styles';
import MuiTextField from '@material-ui/core/TextField';

const Input = withStyles(theme => ({
  root: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        borderColor: theme.palette.primary.main,
      },
    },
    backgroundColor: theme.palette.primary.light,
    borderRadius: 4,
    margin: '0 0px 8px 0',
  },
}))(MuiTextField);

Input.defaultProps = {
  fullWidth: true,
  margin: 'dense',
  variant: 'outlined',
  inputProps: { style: { padding: '11px' } },
};

export default Input;
