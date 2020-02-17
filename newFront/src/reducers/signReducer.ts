import produce from 'immer'
import {
  MAIL_VALUE_CHANGE_REQUESTED,
  NAME_VALUE_CHANGE_REQUESTED,
  PWD_VALUE_CHANGE_REQUESTED,
  PWD_CHECK_VALUE_CHANGE_REQUESTED
} from '../actionCmds/signActionCmd'
import { singActionTypes } from '../actions/signAction'

const initMap = {
  mail: '',
  name: '',
  pwd: '',
  pwdChk: ''
}

const signReducer = (state = initMap, action: singActionTypes) => {
  let nextState = state

  switch (action.type) {
    case MAIL_VALUE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.mail = action.mail
      })
      break
    case NAME_VALUE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.name = action.name
      })
      break
    case PWD_VALUE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.pwd = action.pwd
      })
      break
    case PWD_CHECK_VALUE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.pwdChk = action.pwdChk
      })
      break
    default:
      break
  }
  return nextState
}

export default signReducer
