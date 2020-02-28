import { getInstance } from '../common/common'

const instant = getInstance()

export const getOrderForStockApi = (exchange: string) => {
  const result = instant.get(`order/${exchange}`)
  return result
}
