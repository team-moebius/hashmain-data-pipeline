import { createStore, applyMiddleware } from 'redux'
import createSagaMiddleware from 'redux-saga'
import logger from 'redux-logger'
import rootReducer from '../reducers/rootReducer'
import rootSaga from '../sagas/rootSaga'
import initialState from './rootState'

const sagaMiddleware = createSagaMiddleware()

const configureStore = () => {
  const store = createStore(
    rootReducer,
    initialState,
    process.env.NODE_ENV === 'development'
      ? applyMiddleware(sagaMiddleware, logger) : applyMiddleware(sagaMiddleware)
  )
  sagaMiddleware.run(rootSaga)
  return store
}

export default configureStore
