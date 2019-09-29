import * as React from 'react';
import MuiTextField, { TextFieldProps as MuiTextFieldProps } from '@material-ui/core/TextField';
import { withStyles, makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  input: {
    padding: '11px',
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
        borderColor: theme.palette.primary.main,
      },
    },
    backgroundColor: theme.palette.primary.light,
    borderRadius: 4,
    margin: '8px 0px 0px 0',
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
  margin: 'dense',
  variant: 'outlined',
  inputProps: { style: { padding: '11px' } },
};

export default Input;
