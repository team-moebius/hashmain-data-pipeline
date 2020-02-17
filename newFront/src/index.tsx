import * as React from 'react'
import { render } from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import 'react-app-polyfill/ie9'
import configureStore from './common/store'

// import 'antd/dist/antd.css'

import './index.css'
import Routers from './common/router/routers'

const store = configureStore()

const Root = (
  <Provider store={store}>
    <BrowserRouter>
      <Routers />
    </BrowserRouter>
  </Provider>
)

render(
  Root,
  document.getElementById('root') as HTMLElement
)
