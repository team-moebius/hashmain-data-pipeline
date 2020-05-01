import { put, call, select } from 'redux-saga/effects'
import {
  getOrderForStockApi, fetchOrderForStockApi, fetchAPIKeyAPi, getMarketApi, getAssetsApi, getManagesApi
} from '../../apis/htsApi'
import { ReducerState } from '../../reducers/rootReducer'
import {
  HTS_TRADE_INFO_SUCCESS,
  HTS_TRADE_INFO_FAILED,
  HTS_TRADE_ORDER_SUCCESS,
  HTS_TRADE_ORDER_FAILED,
  HTS_API_KEY_SUCCESS,
  HTS_API_KEY_FAILED,
  HTS_MARKET_INFO_SUCCESS,
  HTS_MARKET_INFO_FAILED,
  HTS_ASSETS_SUCCESS,
  HTS_ASSETS_FAILED,
  HTS_MANAGES_SUCCESS,
  HTS_MANAGES_FAILED
} from '../../actionCmds/htsActionCmd'
import {
  htsInfoSuccessActionType,
  htsInfoFailedActionType,
  htsOrderSuccessActionType,
  htsOrderFailedActionType,
  htsAPIKeySuccessActionType,
  htsAPIKeyFailedActionType,
  htsMarketSuccessActionType,
  htsMarketFailedActionType,
  htsAssetsSuccessActionType,
  htsAssetsFailedActionType,
  htsMangagesSuccessActionType,
  htsManagesFailedActionType
} from '../../actions/htsAction'

/*
  After asset Api develop, It must be modify function 'refineHTSData', 'refineManageData'
  It have to delete asset part
*/

export function* fetchHtsInfo() {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const { monetaryUnit, stdUnit, exchange } = yield select((state: ReducerState) => ({
      monetaryUnit: state.hts.monetaryUnit,
      stdUnit: state.hts.stdUnit,
      exchange: state.hts.exchange
    }))
    const result = yield call(getOrderForStockApi, exchange, token, monetaryUnit, stdUnit)
    const hts = refineHTSData(result.data)
    const manage = refineManageData(hts)
    yield put(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: hts, manageData: manage }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsInfoFailedActionType({ type: HTS_TRADE_INFO_FAILED, msg: errMsg }))
  }
}

export function* fetchHtsMarketInfo(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(getMarketApi, action.exchange, token)
    const marketDataResult = result.data.map(
      (elm: any) => ({ ...elm, key: elm.symbol.slice(elm.symbol.indexOf('-') + 1) })
    )

    yield put(htsMarketSuccessActionType({ type: HTS_MARKET_INFO_SUCCESS, marketData: marketDataResult }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsMarketFailedActionType({ type: HTS_MARKET_INFO_FAILED, msg: errMsg }))
  }
}

export function* fetchHtsOrder(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(fetchOrderForStockApi, action.dtos, token)
    const hts = refineHTSData(result.data)
    const manage = refineManageData(hts)
    yield put(htsOrderSuccessActionType({ type: HTS_TRADE_ORDER_SUCCESS, htsData: hts, manageData: manage }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsOrderFailedActionType({ type: HTS_TRADE_ORDER_FAILED, msg: errMsg }))
  }
}

export function* fetchAPIKey(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(fetchAPIKeyAPi, action.restType, token)
    yield put(htsAPIKeySuccessActionType({ type: HTS_API_KEY_SUCCESS, answer: result.data }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsAPIKeyFailedActionType({ type: HTS_API_KEY_FAILED, msg: errMsg }))
  }
}

export function* fetchAssets(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(getAssetsApi, action.exchange, token)
    yield put(htsAssetsSuccessActionType({ type: HTS_ASSETS_SUCCESS, assetsData: result.data.assets }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsAssetsFailedActionType({ type: HTS_ASSETS_FAILED, msg: errMsg }))
  }
}

export function* fetchManages(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(getManagesApi, action.exchange, token)
    yield put(htsMangagesSuccessActionType({ type: HTS_MANAGES_SUCCESS, manageData: result.data.orderStatuses }))
  } catch (err) {
    const errMsg = err.response ? err.response.data.message : err.message
    yield put(htsManagesFailedActionType({ type: HTS_MANAGES_FAILED, msg: errMsg }))
  }
}

function refineHTSData(data: { assets: Array<any>, orders: Array<any> }) {
  const title = { SALE: '차 이익실현 지정', PURCHASE: '차 지정', STOPLOSS: '차 감시 지정' }
  const orderType = { LIMIT: '가', MARKET: ' 시장가' }
  const customFilter = (orderPosition: string) => data.orders.filter((elm) => elm.orderPosition === orderPosition)
    .sort((a: {level: number}, b: {level: number}) => (
      orderPosition === 'SALE' ? a.level - b.level : a.level - b.level
    ))
    .map((elm, idx) => ({
      ...elm,
      key: elm.id + idx,
      title: `${title[orderPosition]}${orderType[elm.orderType]}${orderPosition === 'PURCHASE' ? ' 매수' : ' 매도'}`
    }))

  return {
    assets: data.assets,
    sale: customFilter('SALE'),
    purchase: customFilter('PURCHASE'),
    stopLoss: customFilter('STOPLOSS')
  }
}

function refineManageData(data: { assets: any[], sale: any[], purchase: any[], stopLoss: any[] }) {
  const res: { ownCoin: string; average: number; totalVol: any; assessment: number; profit: number }[] = []
  if (!data.assets) { return [] }
  data.assets.forEach((walElm: any) => {
    const walData = data.purchase.filter((elm: any) => elm.symbol.indexOf(walElm.currency) === 0)
    const volume = walData.reduce((a: any, b: { volume: any }) => (a + b.volume), 0)
    const avg = walData.reduce((a: any, b: any) => (a + b.price * b.volume), 0) / volume
    const assessmentMoney = volume * 100 // 100은 현재 시세, 아직 데이터가 없음
    res.push({
      ownCoin: `${walElm.currency}. ${walElm.balance}`,
      average: avg,
      totalVol: volume,
      assessment: assessmentMoney,
      profit: (assessmentMoney / (avg * volume)) * 100
    })
  })

  return res
}
