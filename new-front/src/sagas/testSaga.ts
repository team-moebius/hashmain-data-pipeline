import { call, put } from 'redux-saga/effects'
import { getTestApi } from '../apis/testApi'

export function* fetchTestApi(action: any) {
  try {
    const result = yield call(getTestApi, action.date)

    console.log(result)
    yield put({ type: 'TEST_API_SUCCESS', data: result })
  } catch (err) {
    console.error(err)
    yield put({ type: 'TEST_API_FAILED', data: err })
  }
}
