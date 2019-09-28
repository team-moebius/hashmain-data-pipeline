import React from 'react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const MOBEIUS_THEME = createMuiTheme({
  palette: {
    type: 'dark',
    background: {
      paper: '#0D1E34',
      default: '#091A2E',
    },
    text: {
      primary: '#B3BAD3',
    },
    primary: {
      main: '#0D1E34',
      light: '#162C49',
      dark: '#091A2E',
    },
    secondary: {
      main: '#1E8CDE',
      dark: '#13253F',
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
