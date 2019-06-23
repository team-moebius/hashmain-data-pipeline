import * as React from 'react';

import MuiAppBar, { AppBarProps as MuiAppBarProps } from '@material-ui/core/AppBar';
import MuiToolbar from '@material-ui/core/Toolbar';
import MuiTypography from '@material-ui/core/Typography';

import 'assets/scss/AppBar.scss';

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

const AppBar: React.FC<AppBarProps> = props => {
  const { title, children, subTitle, onClickTitle, ...rest } = props;
  return (
    <MuiAppBar {...rest}>
      <MuiToolbar>
        {children.leftSide}
        <div className="appbar__title-wrapper">
          <MuiTypography className="appbar__title" noWrap color="inherit" onClick={onClickTitle}>
            <em>{title}</em>
          </MuiTypography>
          {subTitle}
        </div>
        {children.rightSide}
      </MuiToolbar>
    </MuiAppBar>
  );
};

export default AppBar;
