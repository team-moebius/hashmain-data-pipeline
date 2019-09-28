import MuiTableRow from '@material-ui/core/TableRow';
import { withStyles } from '@material-ui/styles';

const TableBodyRow = withStyles(theme => ({
  root: {
    backgroundColor: theme.palette.secondary.dark,
  },
}))(MuiTableRow);

TableBodyRow.defaultProps = {};

export default TableBodyRow;
