import * as React from 'react';

import MuiCheckbox, { CheckboxProps as MuiCheckboxProps } from '@material-ui/core/Checkbox';
import MuiFormControlLabel, {
  FormControlLabelProps as MuiFormControlLabelProps,
} from '@material-ui/core/FormControlLabel';

import { Omit } from 'utils/Omit';

export interface CheckboxProps extends Omit<MuiFormControlLabelProps, 'control'> {
  checkboxProps?: MuiCheckboxProps;
}

const Checkbox: React.FC<CheckboxProps> = props => (
  <MuiFormControlLabel {...props} control={<MuiCheckbox {...props.checkboxProps} />} />
);

export default Checkbox;
