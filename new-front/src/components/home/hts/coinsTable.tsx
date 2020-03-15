import React, { useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Table, Button, AutoComplete } from 'antd'
import PerfectScrollbar from 'react-perfect-scrollbar'

import { ReducerState } from '../../../reducers/rootReducer'
import { coinsTableCols } from './coinsTableCols'

interface ICoinsTableProps {
  isRegister: boolean
  setIsRegister: any
}

function unitButtons(unit: string, setUnit: any) {
  const getColor = (key:string, isdisabled: boolean) => {
    if (isdisabled) return '#747474'
    return key === unit ? 'rgb(255, 58, 125)' : '#B7C8F5'
  }

  return (
    <>
      <Button type='link' style={{ color: getColor('KRW', false) }} onClick={() => setUnit('KRW')}>KRW</Button>
      <Button type='link' style={{ color: getColor('BTC', true) }} onClick={() => setUnit('BTC')} disabled>BTC</Button>
      <Button type='link' style={{ color: getColor('ETH', true) }} onClick={() => setUnit('ETH')} disabled>ETH</Button>
      <Button type='link' style={{ color: getColor('USDT', true) }} onClick={() => setUnit('USDT')} disabled>
        USDT</Button>
    </>
  )
}
function getData(isTable: boolean, inputWord: string, htsData: any): [] {
  console.log(isTable, inputWord, htsData)
  return []
}

function CoinsTable(props: ICoinsTableProps) {
  const { isRegister, setIsRegister } = props
  const { htsData } = useSelector((state: ReducerState) => ({ htsData: state.home.htsData }))
  const dispatch = useDispatch()
  const [unit, setUnit] = useState('KRW')
  const [inputWord, setInputWord] = useState('')
  console.log(setIsRegister, dispatch)

  return (
    <div
      className='backgroundColor'
      style={{ marginTop: '10px', height: isRegister ? '266px' : '716px', transition: 'height .15s linear' }}
    >
      {unitButtons(unit, setUnit)}
      <AutoComplete
        className='coinsSearch'
        dataSource={getData(false, inputWord, htsData)}
        placeholder='검색어를 입력해주세요.'
        onSearch={(value) => setInputWord(value)}
      />
      <PerfectScrollbar options={{ wheelPropagation: false }}>
        <Table
          className='customTable'
          columns={coinsTableCols()}
          dataSource={getData(true, inputWord, htsData)}
          size='small'
          pagination={false}
        />
      </PerfectScrollbar>
    </div>
  )
}

export default CoinsTable
