import MuiTab from '@material-ui/core/Tab';
import { withStyles } from '@material-ui/styles';

const TabHeaderItem = withStyles(theme => ({
  root: {
    color: theme.palette.text.primary,
    '&:hover': {
      color: theme.palette.secondary.main,
      opacity: 1,
    },
  },
}))(MuiTab);

TabHeaderItem.defaultProps = {};

export default TabHeaderItem;
