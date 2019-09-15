import React from 'react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const MOBEIUS_THEME = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      main: '#13253F',
      light: '#173456',
      contrastText: '#C9CFE8',
    },
    secondary: {
      main: '#1E8CDE',
    },
  },
  typography: {
    fontSize: 12,
    body1: { fontSize: 12 },
    h6: { fontSize: 14 },
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
