import * as React from 'react';
import classNames from 'classnames';

import { makeStyles } from '@material-ui/core/styles';
import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@material-ui/core/AppBar';
import MuiToolbar from '@material-ui/core/Toolbar';
import MuiTypography from '@material-ui/core/Typography';

export interface AppBarProps extends MuiAppBarProps {
  title: string;
  subTitle?: JSX.Element;
  children: AppBarChildSlots;
  onClickTitle?: (e: React.MouseEvent<HTMLElement>) => void;
}

interface AppBarChildSlots {
  leftSide?: React.ReactChild;
  rightSide?: React.ReactChild;
}

const useStyles = makeStyles(theme => ({
  root: { flexGrow: 1 },
  titleWrapper: { flexGrow: 1 },
  title: {
    fontWeight: 'bold',
    fontSize: 22,
  },
  subTitle: { fontSize: 14 },
}));

const AppBar: React.FC<AppBarProps> = props => {
  const { className, children, subTitle, title, onClickTitle, ...rest } = props;
  const classes = useStyles();
  return (
    <MuiAppBar className={classNames(classes.root, className)} {...rest}>
      <MuiToolbar>
        {children.leftSide}
        <div className={classes.titleWrapper}>
          <MuiTypography className={classes.title} noWrap color="inherit" onClick={onClickTitle}>
            <em>{title}</em>
          </MuiTypography>
          {subTitle && (
            <MuiTypography className={classes.subTitle} noWrap color="inherit">
              {subTitle}
            </MuiTypography>
          )}
        </div>
        {children.rightSide}
      </MuiToolbar>
    </MuiAppBar>
  );
};

export default AppBar;
