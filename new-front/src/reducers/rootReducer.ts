import { combineReducers } from 'redux'
import commonReducer from './commonReducer'
import signReducer from './signReducer'
import homeReducer from './homeReducer'

const rootReducer = combineReducers({
  common: commonReducer,
  sign: signReducer,
  home: homeReducer
})

export type ReducerState = ReturnType<typeof rootReducer>

export default rootReducer
