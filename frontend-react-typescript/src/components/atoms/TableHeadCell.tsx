import MuiTableCell from '@material-ui/core/TableCell';
import { withStyles } from '@material-ui/styles';

const TableHeadCell = withStyles(theme => ({
  root: {
    border: '0',
    color: theme.palette.text.primary,
    fontSize: '0.85em',
    fontWeight: 'bold',
    padding: '0px 9px',
    userSelect: 'none',
  },
}))(MuiTableCell);

TableHeadCell.defaultProps = {};

export default TableHeadCell;
