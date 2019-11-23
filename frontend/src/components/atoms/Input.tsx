import * as React from 'react';
import MuiTextField, { TextFieldProps as MuiTextFieldProps } from '@material-ui/core/TextField';
import { withStyles, makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  input: {
    padding: '10px',
    '&::placeholder': {
      color: theme.palette.text.primary,
      fontSize: '0.8em',
    },
  },
}));

const CustomInput = withStyles(theme => ({
  root: {
    '& .MuiOutlinedInput-root': {
      '& fieldset': {
        border: 0,
      },
    },
    backgroundColor: theme.palette.primary.main,
    borderRadius: 4,
  },
}))(MuiTextField);

const Input: React.FC<MuiTextFieldProps> = props => {
  const classes = useStyles();

  return (
    <CustomInput {...props} InputProps={{ classes: { input: classes.input }, ...props.InputProps }}>
      {props.children}
    </CustomInput>
  );
};

Input.defaultProps = {
  autoComplete: 'off',
  fullWidth: true,
  margin: 'none',
  variant: 'outlined',
};

export default Input;
