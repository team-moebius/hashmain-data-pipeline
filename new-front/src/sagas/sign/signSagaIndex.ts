import { takeEvery, all } from 'redux-saga/effects'
import { SIGN_UP_REQUESTED, SIGN_IN_REQUESTED, MAIL_VALUE_CHANGE_REQUESTED } from '../../actionCmds/signActionCmd'
import { fetchSignUp, fetchDuplicationCheck, fetchSignIn } from './signSaga'

function* signSagas() {
  yield all([
    takeEvery(MAIL_VALUE_CHANGE_REQUESTED, fetchDuplicationCheck),
    takeEvery(SIGN_UP_REQUESTED, fetchSignUp),
    takeEvery(SIGN_IN_REQUESTED, fetchSignIn)
  ])
}

export default signSagas
