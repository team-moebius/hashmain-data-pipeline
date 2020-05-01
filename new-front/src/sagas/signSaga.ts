import { call, put } from 'redux-saga/effects'
import { getDuplicateApi, postSignUpApi, postSignInApi } from '../apis/signApi'
import {
  SIGN_UP_SUCCESS,
  SIGN_UP_FAILED,
  MAIL_DUPLICATION_CHECK_SUCCESS,
  MAIL_DUPLICATION_CHECK_FAILED,
  SIGN_IN_SUCCESS,
  SIGN_IN_FAILED
} from '../actions/commands/signActionCommand'
import {
  signUpFailedAction,
  signUpSuccessAction,
  mailDupFailedAction,
  mailDupSuccessAction,
  signInSuccessAction,
  signInFailedAction
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
    const result = yield call(postSignUpApi, action.mail, action.name, action.password)
    yield put(signUpSuccessAction({ type: SIGN_UP_SUCCESS, signDone: result.data === 'OK' }))
  } catch (err) {
    yield put(signUpFailedAction({ type: SIGN_UP_FAILED, msg: err }))
  }
}

export function* fetchSignIn(action: any) {
  try {
    const result = yield call(postSignInApi, 'burette@hanyang.ac.kr', 'highbal1')
    // const result = yield call(postSignInApi, action.mail, action.pwd)
    window.localStorage.setItem('token', result.data.token)
    // window.location.reload()
    yield put(signInSuccessAction({ type: SIGN_IN_SUCCESS }))
  } catch (err) {
    yield put(signInFailedAction({ type: SIGN_IN_FAILED, msg: err }))
  }
}
