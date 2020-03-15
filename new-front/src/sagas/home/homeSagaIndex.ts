import { takeEvery, all } from 'redux-saga/effects'
import { fetchHtsInfo, fetchHtsOrder, fetchAPIKey } from './htsSaga'
import {
  HTS_TRADE_INFO_REQUESTED,
  HTS_TRADE_ORDER_REQUESTED,
  HTS_API_KEY_REQUESTED
} from '../../actionCmds/htsActionCmd'

function* homeSagas() {
  yield all([
    takeEvery(HTS_TRADE_INFO_REQUESTED, fetchHtsInfo),
    takeEvery(HTS_TRADE_ORDER_REQUESTED, fetchHtsOrder),
    takeEvery(HTS_API_KEY_REQUESTED, fetchAPIKey)
  ])
}

export default homeSagas
