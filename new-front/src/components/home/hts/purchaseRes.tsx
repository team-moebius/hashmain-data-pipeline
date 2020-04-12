import React, { ReactNode } from 'react'
import { Table } from 'antd'
import numeral from 'numeral'

export function purchaseRes(type: string, tableData: any) {
  const data = tableData.purchase || []
  const tableSource = [{
    average: data.reduce((total: number, elm: any) => total + elm.price, 0) / data.length,
    totalVol: data.reduce((total: number, elm: any) => total + elm.volume, 0),
    orderRate: 0,
    totalPrice: data.reduce((total: number, elm: any) => total + elm.price, 0)
  }]

  return (
    <div style={{ width: '100%', backgroundColor: '#1a1c4b', borderBottom: '1px solid #1A1C4B' }}>
      <Table
        className='customTable'
        columns={resCols()}
        dataSource={tableSource}
        size='small'
        pagination={false}
        showHeader={false}
      />
    </div>
  )
}

interface IColsType {
  title: string | ReactNode,
  dataIndex: string,
  key: string,
  width: number,
  render: (value?: any, record?: any, index?: number) => any
}

function resCols(): Array<IColsType> {
  return [{
    title: '',
    dataIndex: '',
    key: '',
    width: 310,
    render: () => {}
  }, {
    title: '',
    dataIndex: 'average',
    key: 'average',
    width: 155,
    render: (text: string) => (
      <>
        <p style={{ textAlign: 'left', fontSize: '11px', marginLeft: '5px', color: '#FF3A7D' }}>매수 평균가</p>
        <p style={{ textAlign: 'right', marginRight: '20px' }}>{numeral(text).format('0.00a')}</p>
      </>
    )
  }, {
    title: '',
    dataIndex: 'totalVol',
    key: 'totalVol',
    width: 155,
    render: (text: string) => (
      <>
        <p style={{ textAlign: 'left', fontSize: '11px', marginLeft: '5px', color: '#FF3A7D' }}>총 매수 수량</p>
        <p style={{ textAlign: 'right', marginRight: '20px' }}>{numeral(text).format('0.00a')}</p>
      </>
    )
  }, {
    title: '',
    dataIndex: 'orderRate',
    key: 'orderRate',
    width: 80,
    render: (text: string) => (
      <>
        <p style={{ textAlign: 'left', fontSize: '11px', marginLeft: '5px', color: '#FF3A7D' }}>주문 비율</p>
        <p style={{ textAlign: 'right', marginRight: '10px' }}>{numeral(text).format('0.00a')}%</p>
      </>
    )
  }, {
    title: '',
    dataIndex: 'totalPrice',
    key: 'totalPrice',
    width: 155,
    render: (text: string) => (
      <>
        <p style={{ textAlign: 'left', fontSize: '11px', marginLeft: '5px', color: '#FF3A7D' }}>주문 총액</p>
        <p style={{ textAlign: 'right', marginRight: '50px' }}>{numeral(text).format('0.00a')}</p>
      </>
    )
  }]
}
