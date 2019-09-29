import React from 'react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const MOBEIUS_THEME = createMuiTheme({
  palette: {
    type: 'dark',
    background: {
      paper: '#0D1E34',
      default: '#13253f',
    },
    text: {
      primary: '#C7CDDB',
      secondary: '#657486',
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
  typography: {
    caption: {
      fontSize: '0.825em',
    },
    fontFamily: [
      'Malgun Gothic',
      '맑은 고딕',
      '-apple-system',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
      '"Apple Color Emoji"',
      '"Segoe UI Emoji"',
      '"Segoe UI Symbol"',
    ].join(','),
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
