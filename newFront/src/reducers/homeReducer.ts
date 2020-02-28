import produce from 'immer'
import {
  MENU_MODE_CHANGE_REQUESTED,
  HTS_TRADE_INFO_SUCCESS
} from '../actionCmds/homeActionCmd'
import { homeTypes } from '../actions/homeAction'

const initMap = {
  menuMode: 'android',
  htsData: [{}]
}

const homeReducer = (state = initMap, action: homeTypes) => {
  let nextState = state

  switch (action.type) {
    case MENU_MODE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.menuMode = action.menuMode
      })
      break
    case HTS_TRADE_INFO_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.htsData = action.htsData
      })
      break
    default:
      break
  }
  return nextState
}

export default homeReducer
