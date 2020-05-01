import React from 'react'
import numeral from 'numeral'

export function manageCols() {
  return [{
    title: <div style={{ height: '30px', width: '0' }} />,
    dataIndex: '',
    key: '',
    algin: 'center',
    className: 'firstCol',
    render: () => <div className='firstCol' style={{ height: '30px', width: '0' }} />
  }, {
    title: '보유 코인',
    dataIndex: 'currency',
    key: 'currency',
    algin: 'center',
    render: (text: string) => <p>{text}</p>
  }, {
    title: '평균 매수가',
    dataIndex: 'averagePurchasePrice',
    key: 'averagePurchasePrice',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: '매수량',
    dataIndex: 'balance',
    key: 'balance',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: '거래 가격',
    dataIndex: 'tradePrice',
    key: 'tradePrice',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: '현재 평가 금액',
    dataIndex: 'evaluatedPrice',
    key: 'evaluatedPrice',
    algin: 'center',
    render: (text: number) => (Number.isNaN(text) || text === 0 ? '-' : numeral(text).format('0,0'))
  }, {
    title: '현재 평가 손익',
    dataIndex: 'profitLossRatio',
    key: 'profitLossRatio',
    algin: 'center',
    render: (text: number) => (
      Number.isNaN(text) || text === 0 ? '-'
        : <p className={text < 0 ? 'lossColor' : 'profitColor'}>{numeral(text).format('0.00')}%</p>
    )
  }, {
    title: '주문 상태',
    dataIndex: 'orderStatus',
    key: 'orderStatus',
    algin: 'center',
    render: (text: number) => <p>{text}</p>
  }]
}
