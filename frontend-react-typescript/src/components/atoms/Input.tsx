import * as React from 'react';

import { withStyles } from '@material-ui/core/styles';
import MuiTextField, { OutlinedTextFieldProps } from '@material-ui/core/TextField';

interface InputProps extends Omit<OutlinedTextFieldProps, 'fullWidth' | 'margin' | 'variant'> {}

const MobeiusInput = withStyles(theme => ({
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

const Input: React.FunctionComponent<InputProps> = props => {
  const { className, ...rest } = props;
  return (
    <MobeiusInput
      fullWidth
      inputProps={{
        style: { padding: '11px' },
      }}
      margin="dense"
      variant="outlined"
      {...rest}
    />
  );
};

export default Input;
