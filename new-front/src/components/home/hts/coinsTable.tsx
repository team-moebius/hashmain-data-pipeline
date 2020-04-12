import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Table, Button, Input, Card, Spin } from 'antd'

import { HTS_MARKET_INFO_REQUESTED, HTS_STD_UNIT_CHANGE } from '../../../actionCmds/htsActionCmd'
import { htsMarketActionActionType, htsStdUnitChangeActionType } from '../../../actions/htsAction'
import { ReducerState } from '../../../reducers/rootReducer'
import { coinsTableCols } from './coinsTableCols'

interface ICoinsTableProps {
  isRegister: boolean
  setIsRegister: any
}

function unitButtons(stdUnit: string, dispatch: any) {
  const getColor = (key:string, isdisabled: boolean) => {
    if (isdisabled) return '#747474'
    return key === stdUnit ? 'rgb(255, 58, 125)' : '#B7C8F5'
  }
  const setStdUnit = (key: string) => dispatch(htsStdUnitChangeActionType({ type: HTS_STD_UNIT_CHANGE, stdUnit: key }))
  return (
    <>
      <Button type='link' style={{ color: getColor('KRW', false) }} onClick={() => setStdUnit('KRW')}>KRW</Button>
      <Button
        type='link' style={{ color: getColor('BTC', true) }} onClick={() => setStdUnit('BTC')} disabled>BTC</Button>
      <Button
        type='link' style={{ color: getColor('ETH', true) }} onClick={() => setStdUnit('ETH')} disabled>ETH</Button>
      <Button type='link' style={{ color: getColor('USDT', true) }} onClick={() => setStdUnit('USDT')} disabled>
        USDT</Button>
    </>
  )
}
function getData(isTable: boolean, inputWord: string, marketData: any): [] {
  if (!marketData[0].key) { return [] }
  if (isTable) {
    return marketData.filter((elm: any) => elm.key.indexOf(inputWord.toUpperCase()) !== -1)
  }

  return marketData.map((elm: any) => elm.key)
}

function CoinsTable(props: ICoinsTableProps) {
  const { isRegister /* setIsRegister */ } = props
  const { marketData, dataLoading, monetaryUnit, stdUnit, nowExchange } = useSelector((state: ReducerState) => ({
    marketData: state.hts.marketData,
    monetaryUnit: state.hts.monetaryUnit,
    stdUnit: state.hts.stdUnit,
    dataLoading: state.common.loading['HTS_MARKET_INFO'],
    nowExchange: state.hts.exchange
  }))
  const dispatch = useDispatch()
  const [inputWord, setInputWord] = useState('')

  useEffect(() => {
    dispatch(htsMarketActionActionType({ type: HTS_MARKET_INFO_REQUESTED, exchange: nowExchange }))
  }, [dispatch, nowExchange])

  return (
    <div
      className='backgroundColor'
      style={{ marginTop: '10px', height: isRegister ? '370px' : '819px', transition: 'height .15s linear' }}
    >
      {unitButtons(stdUnit, dispatch)}
      <Input
        style={{ margin: '5px 10px', width: '93%' }}
        placeholder='검색어를 입력해주세요.'
        onChange={(input) => setInputWord(input.target.value)}
      />
      <div style={{ height: isRegister ? '300px' : '719px', transition: 'height .15s linear' }}>
        <Spin spinning={dataLoading}>
          <Card className='coinsTable' loading={dataLoading}>
            <Table
              className='customTable'
              columns={coinsTableCols(monetaryUnit, dispatch)}
              dataSource={getData(true, inputWord, marketData)}
              size='small'
              pagination={false}
              scroll={{ y: isRegister ? '230px' : '679px' }}
            />
          </Card>
        </Spin>
      </div>
    </div>
  )
}

export default CoinsTable
