import { put, delay } from 'redux-saga/effects'
// import { getOrderForStockApi } from '../../apis/homeApi'
import { HTS_TRADE_INFO_SUCCESS, HTS_TRADE_INFO_FAILED } from '../../actions/commands/homeActionCommand'
import { htsInfoSuccessActionType, htsInfoFailedActionType } from '../../actions/homeAction'

const data = [
  {
    key: '1',
    title: 'hohohoho',
    price1: 20000,
    amount: 20,
    rate: 25,
    price2: 200000
  },
  {
    key: '2',
    title: 'qqqqq',
    price1: 10000,
    amount: 10,
    rate: 5,
    price2: 100000
  },
  {
    key: '3',
    title: 'xxxxxx',
    price1: 5000,
    amount: 56,
    rate: 22,
    price2: 11
  }
]

export function* fetchHtsInfo(action: any) {
  try {
    // const exchange = 'upbit'
    // const temp = window.localStorage.getItem('token')
    // console.log(temp)
    // const result = yield call(getOrderForStockApi, exchange)
    // console.log(result)
    yield delay(1000)
    yield put(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: data }))
  } catch (err) {
    yield put(htsInfoFailedActionType({ type: HTS_TRADE_INFO_FAILED, msg: err }))
  }
}
