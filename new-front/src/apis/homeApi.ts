import { getInstance } from '../common/common'

const instance = getInstance()

export const getOrderForStockApi = (exchange: string, token: string) => {
  const result = instance.get(`orders/${exchange}`, {
    headers: { Authorization: `Bearer ${token}` }
  })
  return result
}

export const fetchOrderForStockApi = (data: Array<Object>, token: string) => {
  const result = instance.post('orders', {
    headers: { Authorization: `Bearer ${token}` },
    orderDtos: data
  })
  return result
}

export const fetchAPIKeyAPi = (restType: { type: string, data?: Object | string }, token: string) => {
  let result
  if (restType.type === 'post') {
    result = instance.post('api-keys', {
      headers: { Authorization: `Bearer ${token}` },
      apiKeyDto: restType.data
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
