import * as React from 'react';
import MuiIconButton, { IconButtonProps as MuiIconButtonProps } from '@material-ui/core/IconButton';
import { withStyles } from '@material-ui/core/styles';

const CustomIconButton = withStyles(theme => ({
  root: {
    '&:hover': {
      color: theme.palette.secondary.main,
    },
  },
}))(MuiIconButton);

export interface IconButtonProps extends MuiIconButtonProps {
  icon: JSX.Element;
}

const IconButton: React.FC<IconButtonProps> = props => {
  const { icon, ...rest } = props;
  return <CustomIconButton {...rest}>{icon}</CustomIconButton>;
};

IconButton.defaultProps = {
  color: 'inherit',
};

export default IconButton;
