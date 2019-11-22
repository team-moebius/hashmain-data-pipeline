import MuiTab from '@material-ui/core/Tab';
import { withStyles } from '@material-ui/styles';

const TabHeaderItem = withStyles(theme => ({
  root: {
    color: theme.palette.text.primary,
    fill: theme.palette.text.primary,
    '&.Mui-selected': {
      fill: theme.palette.secondary.main,
    },
    '&:hover': {
      color: theme.palette.secondary.main,
      fill: theme.palette.secondary.main,
      opacity: 1,
    },
  },
}))(MuiTab);

TabHeaderItem.defaultProps = {};

export default TabHeaderItem;
