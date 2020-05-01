import 'react-app-polyfill/ie9'
import 'react-app-polyfill/stable'

import * as React from 'react'
import { render } from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'

import configureStore from './common/store'
import Routers from './common/router/routers'
import Particle from './common/particle'

import './index.css'

const store = configureStore()

const Root = (
  <div style={{ height: '100%' }}>
    <Provider store={store}>
      <BrowserRouter>
        <Particle />
        <Routers />
      </BrowserRouter>
    </Provider>
  </div>
)

render(
  Root,
  document.getElementById('root') as HTMLElement
)
