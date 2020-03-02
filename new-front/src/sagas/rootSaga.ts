import { takeEvery, all } from 'redux-saga/effects'
import { SIGN_UP_REQUESTED, SIGN_IN_REQUESTED, MAIL_VALUE_CHANGE_REQUESTED } from '../actions/commands/signActionCommand'
import { fetchSignUp, fetchDuplicationCheck, fetchSignIn } from '../sagas/signSaga'
import { HTS_TRADE_INFO_REQUESTED } from '../actions/commands/homeActionCommand'
import { fetchHtsInfo } from './home/htsSaga'

function* rootSaga() {
  yield all([
    takeEvery(MAIL_VALUE_CHANGE_REQUESTED, fetchDuplicationCheck),
    takeEvery(SIGN_UP_REQUESTED, fetchSignUp),
    takeEvery(SIGN_IN_REQUESTED, fetchSignIn),
    takeEvery(HTS_TRADE_INFO_REQUESTED, fetchHtsInfo)
  ])
}

export default rootSaga
