import { takeEvery, all } from 'redux-saga/effects'
import { fetchHtsInfo, fetchHtsOrder, fetchAPIKey, fetchHtsMarketInfo, fetchAssets } from './htsSaga'
import {
  HTS_TRADE_INFO_REQUESTED,
  HTS_TRADE_ORDER_REQUESTED,
  HTS_API_KEY_REQUESTED,
  HTS_MARKET_INFO_REQUESTED,
  HTS_ASSETS_REQUESTED
} from '../../actionCmds/htsActionCmd'

function* homeSagas() {
  yield all([
    takeEvery(HTS_TRADE_INFO_REQUESTED, fetchHtsInfo),
    takeEvery(HTS_TRADE_ORDER_REQUESTED, fetchHtsOrder),
    takeEvery(HTS_API_KEY_REQUESTED, fetchAPIKey),
    takeEvery(HTS_MARKET_INFO_REQUESTED, fetchHtsMarketInfo),
    takeEvery(HTS_ASSETS_REQUESTED, fetchAssets)
  ])
}

export default homeSagas
