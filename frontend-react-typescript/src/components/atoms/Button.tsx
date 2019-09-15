import * as React from 'react';
import MuiButton, { ButtonProps as MuiButtonProps } from '@material-ui/core/Button';

export interface Buttonrops extends MuiButtonProps {}

const Button: React.FC<Buttonrops> = props => <MuiButton {...props}>{props.children}</MuiButton>;

Button.defaultProps = {
  color: 'secondary',
  fullWidth: true,
  size: 'large',
  type: 'submit',
  variant: 'contained',
};

export default Button;
