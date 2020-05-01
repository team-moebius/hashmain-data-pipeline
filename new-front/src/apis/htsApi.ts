import { getInstance } from '../common/common'

const instance = getInstance()

export const getOrderForStockApi = (exchange: string, token: string, monetaryUnit: string, stdUnit: string) => {
  const result = instance.get(`orders/${exchange}/${stdUnit}-${monetaryUnit}`, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}

export const fetchOrderForStockApi = (data: Array<Object>, token: string) => {
  const result = instance.post('orders', data, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}

export const fetchAPIKeyAPi = (restType: { type: string, data?: Object | string }, token: string) => {
  let result
  if (restType.type === 'post') {
    result = instance.post('api-keys', restType.data, {
      headers: { Authorization: `Bearer ${token}` }
    })
  } else if (restType.type === 'del') {
    result = instance.delete(`api-keys/${restType.data}`, {
      headers: { Authorization: `Bearer ${token}` }
    })
  } else {
    result = instance.get('api-keys', {
      headers: { Authorization: `Bearer ${token}` }
    })
  }
  return result
}

export const getMarketApi = (exchange: string, token: string) => {
  const result = instance.get(`markets/${exchange}`, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}

export const getAssetsApi = (exchange: string, token: string) => {
  const result = instance.get(`assets/${exchange}`, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}

export const getManagesApi = (exchange: string, token: string) => {
  const result = instance.get(`orders/status/exchanges/${exchange}`, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}
