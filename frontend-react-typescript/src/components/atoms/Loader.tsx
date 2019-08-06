import * as React from 'react';
import classNames from 'classnames';
import CircularProgress from '@material-ui/core/CircularProgress';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles(theme => ({
  wrapper: {
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
  helperText: { marginTop: '15px', color: theme.palette.primary.contrastText },
}));

export interface LoaderProps {
  textClassName?: string;
  helperText?: string;
  iconSize?: number;
}

const Loader: React.FC<LoaderProps> = props => {
  const classes = useStyles();
  return (
    <div className={classes.wrapper}>
      <CircularProgress color="secondary" disableShrink size={props.iconSize} />
      {props.helperText && (
        <span className={classNames(classes.helperText, props.textClassName)}>
          {props.helperText}
        </span>
      )}
      {props.children}
    </div>
  );
};

export default Loader;
