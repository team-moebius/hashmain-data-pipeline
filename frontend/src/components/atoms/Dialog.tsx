import MuiDialog, { DialogProps as MuiDialogProps } from '@material-ui/core/Dialog';
import { withStyles } from '@material-ui/styles';

export interface DialogProps extends MuiDialogProps {}

const Dialog = withStyles(theme => ({}))(MuiDialog);

Dialog.defaultProps = {};

export default Dialog;
