import * as React from 'react';

import MuiTypography, { TypographyProps as MuiTypographyProps } from '@material-ui/core/Typography';

export interface TextProps extends MuiTypographyProps {}

const Text: React.FC<TextProps> = props => {
  return <MuiTypography {...props}>{props.children}</MuiTypography>;
};

export default Text;
