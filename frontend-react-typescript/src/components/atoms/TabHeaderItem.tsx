import MuiTab from '@material-ui/core/Tab';
import { withStyles } from '@material-ui/styles';

const MoebTabHeaderItem = withStyles(theme => ({}))(MuiTab);

MoebTabHeaderItem.defaultProps = {
  disableRipple: true,
};

export default MoebTabHeaderItem;
