import produce from 'immer'
import {
  MAIL_VALUE_CHANGE_REQUESTED,
  NAME_VALUE_CHANGE_REQUESTED,
  PWD_VALUE_CHANGE_REQUESTED,
  PWD_CHECK_VALUE_CHANGE_REQUESTED,
  MAIL_DUPLICATION_CHECK_SUCCESS,
  SIGN_UP_SUCCESS
} from '../actionCmds/signActionCmd'
import { singActionTypes } from '../actions/signAction'

const initMap = {
  mail: '',
  name: '',
  pwd: '',
  pwdChk: '',
  idExist: false,
  signDone: false
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
    case MAIL_DUPLICATION_CHECK_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.idExist = action.idExist
      })
      break
    case SIGN_UP_SUCCESS:
      nextState = produce(state, (draft) => {
        draft.signDone = action.signDone
      })
      break
    default:
      break
  }
  return nextState
}

export default signReducer
