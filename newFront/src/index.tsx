import * as React from 'react'
import { render } from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import 'react-app-polyfill/ie9'
import configureStore from './common/store'

import './index.css'
import Routers from './common/router/routers'
import Particle from './common/particle'

const store = configureStore()

const Root = (
  <Provider store={store}>
    <BrowserRouter>
      <Particle />
      <Routers />
    </BrowserRouter>
  </Provider>
)

render(
  Root,
  document.getElementById('root') as HTMLElement
)
