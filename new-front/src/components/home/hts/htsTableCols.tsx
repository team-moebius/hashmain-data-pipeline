import React, { ReactNode } from 'react'
import { InputNumber, Button } from 'antd'
import produce from 'immer'
import numeral from 'numeral'
import { headerNames } from './headerNames'
// import { HTS_TRADE_INFO_SUCCESS } from '../../../actionCmds/htsActionCmd'
// import { htsInfoSuccessActionType } from '../../../actions/htsAction'

interface IColsType {
  title: string | ReactNode,
  dataIndex: string,
  key: string,
  width?: number,
  render: (value?: any, record?: any, index?: number) => any
}

// stdUnit is diffrent with hts reducer and hts hooks.
// This is hts hooks, after when we hts top button using, It must be change to hts reudcer(Need a policy).
export function htsTableCols(
  type: string,
  stdUnit: string,
  monetaryUnit: string,
  data: any,
  setData: any,
  dispatch: any,
  assetsData: any,
  exchange: string
): Array<IColsType> {
  return [{
    title: '',
    dataIndex: '',
    key: '',
    width: 50,
    render: (text, record, index = 0) => (
      <Button
        type='link'
        className='customLink'
        onClick={() => minusBtnAction(type, data, setData, dispatch, index)} icon='minus-circle'
      />
    )
  }, {
    title: headerNames[type].title,
    dataIndex: 'title',
    key: 'title',
    render: (text, record, index = 0) => (
      <p>{`${type === 'purchase' ? index + 1 : data[type].length - index}${text}`}</p>
    )
  }, {
    title: <Button
      className='customLink'
      type='link'
      onClick={() => plusBtnAction(type, data, setData, dispatch, monetaryUnit, stdUnit, exchange)} icon='plus-circle'
    />,
    dataIndex: '',
    key: '',
    width: 50,
    render: (text, record, index = 0) => (
      <Button
        className='customLink'
        type='link'
        onClick={() => { dataChange(data, type, 'orderType', index, 0, setData) }} icon='sync'
      />
    )
  }, {
    title: headerNames[type].price,
    dataIndex: 'price',
    key: 'price',
    width: 155,
    render: (price, record, index = 0) => (
      <>
        <InputNumber
          style={{ textAlign: 'right' }}
          className='tableInputNumber'
          defaultValue={price}
          onChange={(value) => {
            if (typeof value === 'number') { dataChange(data, type, 'price', index, value, setData) }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>.{stdUnit}</p>
      </>
    )
  }, {
    title: headerNames[type].volume,
    dataIndex: 'volume',
    key: 'volume',
    width: 155,
    render: (volume, record, index = 0) => (
      <>
        <InputNumber
          className='tableInputNumber'
          defaultValue={volume}
          onChange={(value) => {
            if (typeof value === 'number') { dataChange(data, type, 'volume', index, value, setData) }
          }}
          formatter={(value) => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
          parser={(value = '') => value.replace(/\$\s?|(,*)/g, '')}
        />
        <p style={{ display: 'inline-block', marginLeft: '5px' }}>.{monetaryUnit}</p>
      </>
    )
  }, {
    title: headerNames[type].rate,
    dataIndex: 'rate',
    key: 'rate',
    width: 80,
    render: (empty, record) => {
      const { volume } = record
      const getBalance = (unit: string) => {
        const nowBalnce = assetsData.filter((elm: any) => elm.currency === unit)
        return nowBalnce[0] ? nowBalnce[0].balance : 0
      }
      const balance = type === 'purchase' ? getBalance(monetaryUnit) : getBalance(stdUnit)
      return <p style={{ textAlign: 'center' }}>{balance === 0 ? 0 : numeral(volume / balance).format('0.0')}%</p>
    }
  },
  {
    title: headerNames[type].total,
    dataIndex: 'total',
    key: 'total',
    width: 155,
    render: (empty, record) => <p>{`${numeral(record.price * record.volume).format(',0.00a')} .${stdUnit}`}</p>
  }]
}

function dataChange(data: any, type: string, key: string, index: number, value: number, setData: any) {
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

function plusBtnAction(
  type: string, data: any, setData: any, dispatch: any, monetaryUnit: string, stdUnit: string, exchange: string
): void {
  const title = { SALE: '차 이익실현 지정가 ', PURCHASE: '차 지정가 ', STOPLOSS: '차 감시 지정가 ' }
  const newElm = {
    id: '',
    eventType: 'CREATE',
    exchange: exchange.toUpperCase(),
    symbol: `${stdUnit}-${monetaryUnit}`,
    orderPosition: type.toUpperCase(),
    orderType: 'LIMIT',
    price: 0,
    volume: 0,
    level: data[type].length,
    key: data[type].length,
    title: `${title[type.toUpperCase()]}${type === 'purchase' ? '매수' : '매도'}`
  }
  const newData = produce(data, (draft: any) => {
    if (type === 'purchase') {
      draft[type].push(newElm)
    } else {
      draft[type].unshift(newElm)
    }
  })
  setData(newData)
  // dispatch(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: newData }))
}

function minusBtnAction(type: string, data: any, setData: any, dispatch: any, index: number): void {
  const newData = produce(data, (draft: any) => {
    draft[type].splice(index, 1)
  })
  setData(newData)
  // dispatch(htsInfoSuccessActionType({ type: HTS_TRADE_INFO_SUCCESS, htsData: newData }))
}
