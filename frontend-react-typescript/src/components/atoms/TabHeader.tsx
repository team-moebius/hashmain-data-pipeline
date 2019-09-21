import MuiTabs from '@material-ui/core/Tabs';
import { withStyles } from '@material-ui/styles';

const MoebTabHeader = withStyles(theme => ({}))(MuiTabs);

MoebTabHeader.defaultProps = {
  variant: 'scrollable',
  indicatorColor: 'secondary',
  textColor: 'secondary',
};

export default MoebTabHeader;
