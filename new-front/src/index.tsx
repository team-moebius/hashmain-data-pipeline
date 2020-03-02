import * as React from 'react'
import { render } from 'react-dom'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux'
import PerfectScrollbar from 'react-perfect-scrollbar'

import configureStore from './common/store'
import Routers from './common/router/routers'
import Particle from './common/particle'

import 'react-app-polyfill/ie9'
import './index.css'
import 'react-perfect-scrollbar/dist/css/styles.css'

const store = configureStore()

const Root = (
  <div style={{ height: '100%' }}>
    <PerfectScrollbar>
      <Provider store={store}>
        <BrowserRouter>
          <Particle />
          <Routers />
        </BrowserRouter>
      </Provider>
    </PerfectScrollbar>
  </div>
)

render(
  Root,
  document.getElementById('root') as HTMLElement
)
