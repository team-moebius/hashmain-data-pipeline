import { call, put } from 'redux-saga/effects'
import { getDuplicateApi, getSignUpApi } from '../apis/signApi'
import { SIGN_UP_SUCCESS, SIGN_UP_FAILED } from '../actionCmds/signActionCmd'
import { signUpFailedAction, signUpSuccessAction } from '../actions/signAction'

export function* fetchSignUp(action: any) {
  try {
    const duplicatieCheck = yield call(getDuplicateApi, action.mail)
    if (duplicatieCheck.data) {
      yield put(signUpFailedAction({ type: SIGN_UP_FAILED, msg: '이미 존재하는 ID 입니다.' }))
    } else {
      yield call(getSignUpApi, action.mail, action.name, action.pwd)
      yield put(signUpSuccessAction({ type: SIGN_UP_SUCCESS, msg: '가입 완료' }))
    }
  } catch (err) {
    yield put(signUpFailedAction({ type: SIGN_UP_FAILED, msg: err }))
  }
}
