import * as React from 'react';
import MuiIconButton, { IconButtonProps as MuiIconButtonProps } from '@material-ui/core/IconButton';
import { withStyles } from '@material-ui/core/styles';

const CustomIconButton = withStyles(theme => ({
  sizeSmall: { padding: '4px' },
}))(MuiIconButton);

export interface IconButtonProps extends MuiIconButtonProps {
  icon: JSX.Element;
}

const IconButton: React.FC<IconButtonProps> = props => {
  const { icon, ...rest } = props;
  return <CustomIconButton {...rest}>{icon}</CustomIconButton>;
};

IconButton.defaultProps = {
  size: 'small',
  color: 'inherit',
};

export default IconButton;
