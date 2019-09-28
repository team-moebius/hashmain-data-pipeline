import MuiTableCell from '@material-ui/core/TableCell';
import { withStyles } from '@material-ui/styles';

const TableHeadCell = withStyles(theme => ({
  root: {
    border: '0',
    padding: '0px 9px',
  },
}))(MuiTableCell);

TableHeadCell.defaultProps = {};

export default TableHeadCell;
