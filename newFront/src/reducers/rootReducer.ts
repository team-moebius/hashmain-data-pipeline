import { combineReducers } from 'redux'
import testReducer from './testReducer'
import commonReducer from './commonReducer'
import signReducer from './signReducer'
import homeReducer from './homeReducer'

const rootReducer = combineReducers({
  test: testReducer,
  common: commonReducer,
  sign: signReducer,
  home: homeReducer
})

export type ReducerState = ReturnType<typeof rootReducer>

export default rootReducer
