import * as React from 'react';

import Checkbox from 'components/atoms/Checkbox';
import { CheckboxProps } from "@material-ui/core/Checkbox";
import MuiFormControlLabel, {
  FormControlLabelProps as MuiFormControlLabelProps,
} from '@material-ui/core/FormControlLabel';

import { Omit } from 'utils/Omit';

export interface CheckboxFormProps extends Omit<MuiFormControlLabelProps, 'control'> {
  checkboxProps?: CheckboxProps;
}

const CheckboxForm: React.FC<CheckboxFormProps> = props => (
  <MuiFormControlLabel {...props} control={<Checkbox {...props.checkboxProps} />} />
);

export default CheckboxForm;
