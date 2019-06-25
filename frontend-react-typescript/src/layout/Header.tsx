import * as React from 'react';

import MuiButton from '@material-ui/core/Button';
import MuiTypography from '@material-ui/core/Typography';

import AppBar from 'components/molecules/AppBar';

import logo from 'assets/images/logo.png';

export interface HeaderProps {
  onClickAlertSample?: (e: React.MouseEvent<HTMLElement>) => void;
  onClickSignOut?: (e: React.MouseEvent<HTMLElement>) => void;
}

const Header: React.FC<HeaderProps> = props => (
  <AppBar
    className="layout-header"
    position="absolute"
    subTitle={
      <>
        <em>H</em>ome <em>T</em>rading <em>S</em>ystem
      </>
    }
    title="CRYPTO BOX GLOBAL."
  >
    {{
      leftSide: <img alt="logo" className="layout-header__logo" src={logo} />,
      rightSide: (
        <>
          <MuiButton
            className="layout-header__button"
            size="medium"
            onClick={props.onClickAlertSample}
          >
            <MuiTypography variant="h6">얼럿 샘플</MuiTypography>
          </MuiButton>
          <MuiButton className="layout-header__button" size="medium" onClick={props.onClickSignOut}>
            <MuiTypography variant="h6">로그 아웃</MuiTypography>
          </MuiButton>
        </>
      ),
    }}
  </AppBar>
);

export default Header;
