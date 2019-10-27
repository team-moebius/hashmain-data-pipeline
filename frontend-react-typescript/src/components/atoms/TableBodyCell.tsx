import MuiTableCell from '@material-ui/core/TableCell';
import { withStyles } from '@material-ui/styles';

const TableBodyCell = withStyles(theme => ({
  root: {
    // backgroundColor: theme.palette.primary.main,
    borderTop: '3px solid',
    borderTopColor: theme.palette.background.paper,
    borderBottom: '0',
    fontSize: '0.85em',
    padding: '3px 9px',
    marginTop: '1px',
    // userSelect: 'none',
  },
}))(MuiTableCell);

TableBodyCell.defaultProps = {};

export default TableBodyCell;
