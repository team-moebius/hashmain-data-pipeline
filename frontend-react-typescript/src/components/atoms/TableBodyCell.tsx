import MuiTableCell from '@material-ui/core/TableCell';
import { withStyles } from '@material-ui/styles';

const TableBodyCell = withStyles(theme => ({
  root: {
    borderTop: '3px solid',
    borderTopColor: theme.palette.primary.main,
    borderBottom: '0',
    fontSize: '0.85em',
    padding: '4px 9px',
    marginTop: '1px',
    // userSelect: 'none',
  },
}))(MuiTableCell);

TableBodyCell.defaultProps = {};

export default TableBodyCell;
