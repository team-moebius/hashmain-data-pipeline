import MuiTable from '@material-ui/core/Table';
import { withStyles } from '@material-ui/styles';

const Table = withStyles(theme => ({}))(MuiTable);

Table.defaultProps = {
  stickyHeader: true,
  size: 'medium',
};

export default Table;
