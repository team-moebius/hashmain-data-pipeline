import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';
import Typography, { TypographyProps as MuiTypographyProps } from '@material-ui/core/Typography';

interface TextProps extends MuiTypographyProps {}

const useStyles = makeStyles(theme => ({
  root: {},
}));

const TextForButton: React.FC<TextProps> = props => {
  const { className, ...rest } = props;
  const classes = useStyles();
  return <Typography className={classNames(classes.root, className)} {...rest} />;
};

export default TextForButton;
