import { call, put } from 'redux-saga/effects'
import { getDuplicateApi, getSignUpApi } from '../apis/signApi'
import {
  SIGN_UP_SUCCESS,
  SIGN_UP_FAILED,
  MAIL_DUPLICATION_CHECK_SUCCESS,
  MAIL_DUPLICATION_CHECK_FAILED
} from '../actionCmds/signActionCmd'
import {
  signUpFailedAction,
  signUpSuccessAction,
  mailDupFailedAction,
  mailDupSuccessAction
} from '../actions/signAction'

export function* fetchDuplicationCheck(action: any) {
  try {
    const result = yield call(getDuplicateApi, action.mail)
    const isExist = !!result.data
    yield put(mailDupSuccessAction({ type: MAIL_DUPLICATION_CHECK_SUCCESS, idExist: isExist }))
  } catch (err) {
    yield put(mailDupFailedAction({ type: MAIL_DUPLICATION_CHECK_FAILED, msg: err }))
  }
}

export function* fetchSignUp(action: any) {
  try {
    const result = yield call(getSignUpApi, action.mail, action.name, action.pwd)
    console.log(result)
    yield put(signUpSuccessAction({ type: SIGN_UP_SUCCESS, signDone: true }))
  } catch (err) {
    yield put(signUpFailedAction({ type: SIGN_UP_FAILED, msg: err }))
  }
}
