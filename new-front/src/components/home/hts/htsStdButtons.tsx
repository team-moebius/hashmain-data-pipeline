import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Button, Select } from 'antd'

import { ReducerState } from '../../../reducers/rootReducer'
import { HTS_EXCHANGE_UPDATE } from '../../../actionCmds/htsActionCmd'
import { htsExchangeActionType } from '../../../actions/htsAction'

import binance from '../../../images/logos/binance.png'
import bithumb from '../../../images/logos/bithumb.png'
import upbit from '../../../images/logos/upbit.png'
import huobi from '../../../images/logos/huobi.png'

const { Option } = Select

export function HTSBtns(stdUnit: string, setStdUnit: any) {
  const stdKeys = ['KRW', 'BTC', 'ETH', 'USDT']
  const iconSize = { width: '20px', height: '20px', marginRight: '3px' }
  const dispatch = useDispatch()
  const { exchange } = useSelector((state: ReducerState) => ({
    exchange: state.hts.exchange
  }))

  return (
    <div className='backgroundColor htsStdButtons' style={{ opacity: 1 }}>
      {stdKeys.map((value) => (
        <Button
          type='link'
          className='htsStdBtn'
          disabled={value !== 'KRW'}
          style={{
            color: stdUnit === `.${value}` ? 'rgb(255, 58, 125)' : '#B7C8F5',
            opacity: value === 'KRW' ? 1 : 0.5
          }}
          onClick={() => setStdUnit(`.${value}`)}
          key={value}>{value}</Button>
      ))}
      <Select
        defaultValue={exchange}
        className='marketSelect'
        onSelect={(value: string) => {
          dispatch(htsExchangeActionType({ type: HTS_EXCHANGE_UPDATE, exchange: value }))
        }}>
        <Option value='upbit'><img src={upbit} style={iconSize} alt='' />UPBIT</Option>
        <Option value='bithumb' disabled style={{ color: 'rgb(116, 116, 116)' }}>
          <img src={bithumb} style={iconSize} alt='' />BITTHUMB</Option>
        <Option value='binance' disabled style={{ color: 'rgb(116, 116, 116)' }}>
          <img src={binance} style={iconSize} alt='' />BINANCE</Option>
        <Option value='huobi' disabled style={{ color: 'rgb(116, 116, 116)' }}>
          <img src={huobi} style={iconSize} alt='' />HUOBI</Option>
      </Select>
    </div>
  )
}
