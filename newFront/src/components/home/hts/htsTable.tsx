import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { Table } from 'antd'
import { ReducerState } from '../../../reducers/rootReducer'
import { htsTableCols } from './htsTableCols'

interface ITableProps {
  type: string,
  stdCoin: string,
  assets: string
}

function HtsTable(props: ITableProps) {
  const { type, stdCoin, assets } = props
  const { htsData } = useSelector((state: ReducerState) => (
    { htsData: state.home.htsData }
  ))
  const [data, setData] = useState(htsData)
  return (
    <div>
      <Table className='customTable' columns={htsTableCols(type, stdCoin, assets, data, setData)} dataSource={data} size='small' />
    </div>
  )
}

export default HtsTable
