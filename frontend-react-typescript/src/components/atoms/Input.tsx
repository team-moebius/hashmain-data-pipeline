import * as React from 'react';
import MuiTextField, { TextFieldProps as MuiTextFieldProps } from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/core/styles';

const MoebiusInput = withStyles(theme => ({
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

const Input: React.FC<MuiTextFieldProps> = props => <MoebiusInput {...props}>{props.children}</MoebiusInput>;

Input.defaultProps = {
  fullWidth: true,
  margin: 'dense',
  variant: 'outlined',
  inputProps: { style: { padding: '11px' } },
};

export default Input;
