import * as React from 'react';
import classNames from 'classnames';

import MuiPaper, { PaperProps as MuiPaperProps } from '@material-ui/core/Paper';

import 'assets/scss/atoms/Paper.scss';

interface PaperProps extends MuiPaperProps {}

const Paper: React.FunctionComponent<PaperProps> = props => {
  const { className, ...rest } = props;
  return <MuiPaper className={classNames('paper', className)} {...rest} />;
};

export default Paper;
