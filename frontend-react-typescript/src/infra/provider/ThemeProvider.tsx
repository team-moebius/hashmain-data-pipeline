import React from 'react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const MOBEIUS_THEME = createMuiTheme({
  palette: {
    type: 'dark',
    background: {
      paper: '#13253F',
    },
    text: {
      primary: '#C9CFE8',
    },
    primary: {
      main: '#13253F',
      light: '#173456',
    },
    secondary: {
      main: '#1E8CDE',
    },
  },
});

interface ThemeProviderProps {
  children: JSX.Element;
}

const ThemeProvider: React.FC<ThemeProviderProps> = props => (
  <MuiThemeProvider theme={MOBEIUS_THEME}>
    <CssBaseline>{props.children}</CssBaseline>
  </MuiThemeProvider>
);

export default ThemeProvider;
