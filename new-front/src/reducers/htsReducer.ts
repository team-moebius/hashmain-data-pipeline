import produce from 'immer'
import {
  HTS_TRADE_INFO_SUCCESS,
  HTS_API_KEY_REQUESTED,
  HTS_API_KEY_RESET,
  HTS_API_KEY_SUCCESS,
  HTS_API_KEY_FAILED,
  HTS_API_KEY_STATE_CHANGE,
  HTS_MARKET_INFO_SUCCESS,
  HTS_MONEYTRAY_UNIT_CHANGE,
  HTS_STD_UNIT_CHANGE,
  HTS_ASSETS_SUCCESS,
  HTS_EXCHANGE_UPDATE
} from '../actionCmds/htsActionCmd'
import { htsTypes } from '../actions/htsAction'

const initMap = {
  menuMode: 'hts',
  htsData: {},
  manageData: [{}],
  registerValue: {},
  isValid: false,
  apiKeyState: 0, // -1: fail, 0: none, 1: success
  marketData: [{}],
  monetaryUnit: 'BTC',
  stdUnit: 'KRW',
  assetsData: [],
  exchange: 'upbit'
}

const homeReducer = (state = initMap, action: htsTypes) => {
  let nextState = state

  switch (action.type) {
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
    case HTS_MARKET_INFO_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.marketData = action.marketData
      })
      break
    case HTS_MONEYTRAY_UNIT_CHANGE:
      nextState = produce(state, (draft) => {
        draft.monetaryUnit = action.monetaryUnit
      })
      break
    case HTS_STD_UNIT_CHANGE:
      nextState = produce(state, (draft) => {
        draft.stdUnit = action.stdUnit
      })
      break
    case HTS_ASSETS_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.assetsData = action.assetsData
      })
      break
    case HTS_EXCHANGE_UPDATE:
      nextState = produce(state, (draft) => {
        draft.exchange = action.exchange
      })
      break
    default:
      break
  }
  return nextState
}

export default homeReducer
