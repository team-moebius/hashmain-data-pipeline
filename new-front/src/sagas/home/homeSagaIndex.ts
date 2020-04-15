import { takeEvery, all } from 'redux-saga/effects'
import { fetchHtsInfo, fetchHtsOrder, fetchAPIKey, fetchHtsMarketInfo, fetchAssets, fetchManages } from './htsSaga'
import {
  HTS_TRADE_INFO_REQUESTED,
  HTS_TRADE_ORDER_REQUESTED,
  HTS_API_KEY_REQUESTED,
  HTS_MARKET_INFO_REQUESTED,
  HTS_ASSETS_REQUESTED,
  HTS_MANAGES_REQUESTED
} from '../../actionCmds/htsActionCmd'

function* homeSagas() {
  yield all([
    takeEvery(HTS_TRADE_INFO_REQUESTED, fetchHtsInfo),
    takeEvery(HTS_TRADE_ORDER_REQUESTED, fetchHtsOrder),
    takeEvery(HTS_API_KEY_REQUESTED, fetchAPIKey),
    takeEvery(HTS_MARKET_INFO_REQUESTED, fetchHtsMarketInfo),
    takeEvery(HTS_ASSETS_REQUESTED, fetchAssets),
    takeEvery(HTS_MANAGES_REQUESTED, fetchManages)
  ])
}

export default homeSagas
