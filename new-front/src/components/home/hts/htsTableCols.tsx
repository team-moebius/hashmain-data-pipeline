import React, { ReactNode } from 'react'
import { InputNumber, Button } from 'antd'
import produce from 'immer'
import numeral from 'numeral'
import { HTS_TRADE_INFO_SUCCESS } from '../../../actionCmds/htsActionCmd'
import { htsInfoSuccessActionType } from '../../../actions/htsAction'

interface IColsType {
  title: string | ReactNode,
  dataIndex: string,
  key: string,
  render: (value?: any, record?: any, index?: number) => any
}

export function htsTableCols(
  type: string, stdUnit: string, monetaryUnit: string, data: any, setData: any, dispatch: any, htsData: any
): Array<IColsType> {
  return [{
    title: '',
    dataIndex: '',
    key: '',
    render: (text, record, index = 0) => (
      <Button
        type='link'
        className='customLink'
        onClick={() => minusBtnAction(type, data, setData, dispatch, htsData, index)} icon='minus-circle'
      />
    )
  }, {
    title: 'title',
    dataIndex: 'title',
    key: 'title',
    render: (text, record, index = 0) => (
      <p>{`${type === 'purchase' ? index + 1 : data[type].length - index}${text}`}</p>
    )
  }, {
    title: <Button
      className='customLink'
      type='link'
      onClick={() => plusBtnAction(type, data, setData, dispatch, htsData)} icon='plus-circle'
    />,
    dataIndex: '',
    key: '',
    render: (text, record, index = 0) => (
      <Button
        className='customLink'
        type='link'
        onClick={() => { dataChage(data, type, 'orderType', index, 0, setData) }} icon='sync'
      />
    )
  }, {
    title: 'price1',
    dataIndex: 'price',
    key: 'price',
    render: (price, record, index = 0) => (
      <>
        <InputNumber
          style={{ textAlign: 'right' }}
          className='tableInputNumber'
          defaultValue={price}
          onChange={(value) => {
            if (typeof value === 'number') { dataChage(data, type, 'price', index, value, setData) }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>{stdUnit}</p>
      </>
    )
  }, {
    title: 'volume',
    dataIndex: 'volume',
    key: 'volume',
    render: (volume, record, index = 0) => (
      <>
        <InputNumber
          className='tableInputNumber'
          defaultValue={volume}
          onChange={(value) => {
            if (typeof value === 'number') { dataChage(data, type, 'volume', index, value, setData) }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>{monetaryUnit}</p>
      </>
    )
  }, {
    title: 'rate',
    dataIndex: 'rate',
    key: 'rate',
    render: (empty, record) => {
      const { volume } = record
      const getBalance = (unit: string) => {
        const nowBalnce = htsData.assets.filter((elm: any) => elm.currency === unit.slice(1))
        return nowBalnce[0] ? nowBalnce[0].balance : 0
      }
      const balance = type === 'purchase' ? getBalance(monetaryUnit) : getBalance(stdUnit)
      return <p>{balance === 0 ? 0 : numeral(volume / balance).format('0.0')}%</p>
    }
  },
  {
    title: 'price2',
    dataIndex: 'total',
    key: 'total',
    render: (empty, record) => <p>{`${numeral(record.price * record.volume).format(',0')} ${stdUnit}`}</p>
  }]
}

function dataChage(data: any, type: string, key: string, index: number, value: number, setData: any) {
  let temp
  if (key === 'orderType') {
    temp = produce(data, (draft: { [x: string]: { [x: string]: any }[] }) => {
      draft[type][index][key] = draft[type][index][key] === 'LIMIT' ? 'MARKET' : 'LIMIT'
      if (draft[type][index].title.indexOf('시장가') > 0) {
        draft[type][index].title = draft[type][index].title.replace('지정 시장가', '지정가')
      } else {
        draft[type][index].title = draft[type][index].title.replace('가', ' 시장가')
      }
    })
  } else {
    temp = produce(data, (draft: { [x: string]: { [x: string]: number }[] }) => {
      draft[type][index][key] = value
    })
  }

  setData(temp)
}

function plusBtnAction(type: string, data: any, setData: any, dispatch: any, htsData: any): void {
  const title = { SALE: '차 이익실현 지정가 ', PURCHASE: '차 지정가 ', STOPLOSS: '차 감시 지정가 ' }
  const newElm = {
    id: '',
    eventType: 'CREATE',
    exchange: 'UPBIT',
    symbol: 'KRW-ETH',
    orderPosition: type.toUpperCase(),
    orderStatus: 'EXECUTED',
    orderType: 'LIMIT',
    price: 0,
    volume: 0,
    level: data[type].length,
    key: data[type].length,
    title: `${title[type.toUpperCase()]}${type === 'purchase' ? '매수' : '매도'}`
  }
  const newData = produce(htsData, (draft: any) => {
    if (type === 'purchase') {
      draft[type].push(newElm)
    } else {
      draft[type].unshift(newElm)
    }
  })
  setData(newData)
  dispatch(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: newData }))
}

function minusBtnAction(type: string, data: any, setData: any, dispatch: any, htsData: any, index: number): void {
  const newData = produce(htsData, (draft: any) => {
    draft[type].splice(index, 1)
  })
  setData(newData)
  dispatch(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: newData }))
}
