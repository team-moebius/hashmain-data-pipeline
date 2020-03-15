import { put, call } from 'redux-saga/effects'
import { getOrderForStockApi, fetchOrderForStockApi, fetchAPIKeyAPi } from '../../apis/homeApi'
import {
  HTS_TRADE_INFO_SUCCESS,
  HTS_TRADE_INFO_FAILED,
  HTS_TRADE_ORDER_SUCCESS,
  HTS_TRADE_ORDER_FAILED,
  HTS_API_KEY_SUCCESS,
  HTS_API_KEY_FAILED
} from '../../actionCmds/htsActionCmd'
import {
  htsInfoSuccessActionType,
  htsInfoFailedActionType,
  htsOrderSuccessActionType,
  htsOrderFailedActionType,
  htsAPIKeySuccessActionType,
  htsAPIKeyFailedActionType
} from '../../actions/htsAction'

export function* fetchHtsInfo() {
  try {
    const exchange = 'upbit'
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(getOrderForStockApi, exchange, token)
    const hts = refineHTSData(result.data)
    const manage = refineManageData(hts)
    yield put(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: hts, manageData: manage }))
  } catch (err) {
    yield put(htsInfoFailedActionType({ type: HTS_TRADE_INFO_FAILED, msg: err.message }))
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
    yield put(htsOrderFailedActionType({ type: HTS_TRADE_ORDER_FAILED, msg: err.message }))
  }
}

export function* fetchAPIKey(action: any) {
  try {
    const token = window.localStorage.getItem('token') || 'empty'
    const result = yield call(fetchAPIKeyAPi, action.restType, token)
    yield put(htsAPIKeySuccessActionType({ type: HTS_API_KEY_SUCCESS, answer: result.data }))
  } catch (err) {
    yield put(htsAPIKeyFailedActionType({ type: HTS_API_KEY_FAILED, msg: err.message }))
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
