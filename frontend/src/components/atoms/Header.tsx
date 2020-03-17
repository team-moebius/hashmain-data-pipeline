import MuiAppBar from '@material-ui/core/AppBar';
import { withStyles } from '@material-ui/styles';

const Header = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.background.paper,
  },
}))(MuiAppBar);

Header.defaultProps = {};

export default Header;
