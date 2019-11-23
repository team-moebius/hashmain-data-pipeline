import * as React from 'react';
import { makeStyles } from '@material-ui/core/styles';

import CircularProgress from 'components/atoms/CircularProgress';
import Text from 'components/atoms/Text';

const useStyles = makeStyles(theme => ({
  root: {
    alignItems: 'center',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    position: 'absolute',
    textAlign: 'center',
    top: 'calc(50% - 50px)',
    width: '100%',
    zIndex: 1,
  },
}));

export interface LoaderProps {
  text?: string;
  textClassName?: string;
  iconSize?: number;
}

const CircularLoader: React.FC<LoaderProps> = props => {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <CircularProgress size={props.iconSize} />
      {props.text && <Text className={props.textClassName}>{props.text}</Text>}
      {props.children}
    </div>
  );
};

CircularLoader.defaultProps = {
  text: 'Loading..',
};

export default CircularLoader;
