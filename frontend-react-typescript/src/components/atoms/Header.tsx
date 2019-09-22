import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@material-ui/core/AppBar';
import { withStyles } from '@material-ui/styles';

export interface HeaderProps extends MuiAppBarProps {}

const Header = withStyles(theme => ({}))(MuiAppBar);

Header.defaultProps = {};

export default Header;
