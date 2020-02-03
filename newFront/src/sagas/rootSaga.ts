import { takeEvery, all } from 'redux-saga/effects'
import { fetchTestApi } from '../sagas/testSaga'

function* rootSaga() {
  yield all([
    takeEvery('TEST_API_REQUESTED', fetchTestApi)
  ])
}

export default rootSaga
