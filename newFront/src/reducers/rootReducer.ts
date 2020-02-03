import { combineReducers } from 'redux'
import testReducer from './testReducer'
import signReducer from './signReducer'

const rootReducer = combineReducers({
  test: testReducer,
  sign: signReducer
})

export type ReducerState = ReturnType<typeof rootReducer>

export default rootReducer
