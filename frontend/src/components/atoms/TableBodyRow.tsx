import MuiTableRow from '@material-ui/core/TableRow';
import { withStyles } from '@material-ui/styles';

const TableBodyRow = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.primary.main,
  },
}))(MuiTableRow);

TableBodyRow.defaultProps = {
  tabIndex: -1,
};

export default TableBodyRow;
