import { takeEvery, all } from 'redux-saga/effects'
import { fetchTestApi } from '../sagas/testSaga'
import { SIGN_UP_REQUESTED } from '../actionCmds/signActionCmd'
import { fetchSignUp } from '../sagas/signSaga'

function* rootSaga() {
  yield all([
    takeEvery('TEST_API_REQUESTED', fetchTestApi),
    takeEvery(SIGN_UP_REQUESTED, fetchSignUp)
  ])
}

export default rootSaga
