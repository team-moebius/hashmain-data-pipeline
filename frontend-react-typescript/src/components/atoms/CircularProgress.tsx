import * as React from 'react';
import MuiCircularProgress, {
  CircularProgressProps as MuiCircularProgressProps,
} from '@material-ui/core/CircularProgress';

export interface CircularProgressProps extends MuiCircularProgressProps {}

const CircularProgress: React.FC<CircularProgressProps> = props => <MuiCircularProgress {...props} />;

CircularProgress.defaultProps = {
  color: 'secondary',
  disableShrink: true,
};

export default CircularProgress;
