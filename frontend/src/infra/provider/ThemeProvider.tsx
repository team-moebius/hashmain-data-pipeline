import React from 'react';

import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';

const MOBEIUS_THEME = createMuiTheme({
  palette: {
    background: {
      paper: '#101037',
      default: '#151544',
    },
    text: {
      primary: '#B7C8F5',
      secondary: '#657486',
    },
    primary: {
      main: '#1A1C4B',
      light: '#2A84C6',
      dark: '#0D0D2B',
    },
    secondary: {
      main: '#FF3A7D',
      dark: '#B11C3B',
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
