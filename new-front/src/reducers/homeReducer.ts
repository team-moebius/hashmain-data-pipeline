import produce from 'immer'
import { HOME_MENU_CHANGE_REQUESTED } from '../actionCmds/homeActionCmd'
import { homeTypes } from '../actions/homeAction'
import {
  HTS_TRADE_INFO_SUCCESS,
  HTS_API_KEY_REQUESTED,
  HTS_API_KEY_RESET,
  HTS_API_KEY_SUCCESS,
  HTS_API_KEY_FAILED,
  HTS_API_KEY_STATE_CHANGE
} from '../actionCmds/htsActionCmd'
import { htsTypes } from '../actions/htsAction'

const initMap = {
  menuMode: 'hts',
  htsData: {},
  manageData: [{}],
  registerValue: {},
  isValid: false,
  apiKeyState: 0 // -1: fail, 0: none, 1: success
}

const homeReducer = (state = initMap, action: homeTypes | htsTypes) => {
  let nextState = state

  switch (action.type) {
    case HOME_MENU_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.menuMode = action.menuMode
      })
      break
    case HTS_TRADE_INFO_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.htsData = action.htsData
        if (action.manageData) { draft.manageData = action.manageData }
      })
      break
    case HTS_API_KEY_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.registerValue = action.restType.data
      })
      break
    case HTS_API_KEY_RESET:
      nextState = produce(state, (draft) => {
        draft.registerValue = {}
        draft.isValid = false
        draft.apiKeyState = 0
      })
      break
    case HTS_API_KEY_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.isValid = true
        draft.apiKeyState = 1
      })
      break
    case HTS_API_KEY_FAILED:
      nextState = produce(state, (draft) => {
        draft.apiKeyState = -1
      })
      break
    case HTS_API_KEY_STATE_CHANGE:
      nextState = produce(state, (draft) => {
        draft.apiKeyState = action.apiKeyState
      })
      break
    default:
      break
  }
  return nextState
}

export default homeReducer
