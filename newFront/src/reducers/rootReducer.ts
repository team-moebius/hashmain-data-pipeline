import { combineReducers } from 'redux'
import testReducer from './testReducer'
import signReducer from './signReducer'
import homeReducer from './homeReducer'

const rootReducer = combineReducers({
  test: testReducer,
  sign: signReducer,
  home: homeReducer
})

export type ReducerState = ReturnType<typeof rootReducer>

export default rootReducer
