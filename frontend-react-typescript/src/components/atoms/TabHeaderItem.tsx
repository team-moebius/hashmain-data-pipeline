import MuiTab from '@material-ui/core/Tab';
import { withStyles } from '@material-ui/styles';

const TabHeaderItem = withStyles(theme => ({}))(MuiTab);

TabHeaderItem.defaultProps = {
  disableRipple: true,
};

export default TabHeaderItem;
