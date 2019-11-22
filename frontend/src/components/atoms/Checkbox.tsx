import MuiCheckbox, { CheckboxProps as MuiCheckboxProps } from '@material-ui/core/Checkbox';
import { withStyles } from '@material-ui/styles';

export interface CheckboxProps extends MuiCheckboxProps {}

const Checkbox = withStyles(theme => ({}))(MuiCheckbox);

Checkbox.defaultProps = {};

export default Checkbox;
