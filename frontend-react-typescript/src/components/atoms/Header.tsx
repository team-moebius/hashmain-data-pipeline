import * as React from 'react';

import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@material-ui/core/AppBar';
import MuiToolbar from '@material-ui/core/Toolbar';

export interface HeaderProps extends MuiAppBarProps {}

const Header: React.FC<HeaderProps> = props => (
  <MuiAppBar {...props}>
    <MuiToolbar>{props.children}</MuiToolbar>
  </MuiAppBar>
);

export default Header;
