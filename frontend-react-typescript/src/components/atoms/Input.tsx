import * as React from 'react';
import classNames from 'classnames';

import MuiTextField, { OutlinedTextFieldProps } from '@material-ui/core/TextField';

import 'assets/scss/Input.scss';

interface InputProps extends Omit<OutlinedTextFieldProps, 'fullWidth' | 'margin' | 'variant'> {}

const Input: React.FunctionComponent<InputProps> = props => {
  const { className, ...rest } = props;
  return (
    <MuiTextField
      className={classNames('input', props.className)}
      fullWidth
      margin="dense"
      variant="outlined"
      {...rest}
    />
  );
};

export default Input;
