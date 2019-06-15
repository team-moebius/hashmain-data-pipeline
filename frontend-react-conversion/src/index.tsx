import React from 'react';
import "./asset/scss/layout.scss"
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import EntryPage from "./components/templates/EntryPage";
import EntryPageTmp from "./components/templates/EntryPageTmp";


ReactDOM.render(<EntryPageTmp />, document.getElementById('wrap-member'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();