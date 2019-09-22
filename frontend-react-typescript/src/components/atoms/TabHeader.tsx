import MuiTabs from '@material-ui/core/Tabs';
import { withStyles } from '@material-ui/styles';

const TabHeader = withStyles(theme => ({}))(MuiTabs);

TabHeader.defaultProps = {
  variant: 'scrollable',
  indicatorColor: 'secondary',
  textColor: 'secondary',
};

export default TabHeader;
