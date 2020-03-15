import React, { useState } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Table } from 'antd'
import PerfectScrollbar from 'react-perfect-scrollbar'

import { ReducerState } from '../../../reducers/rootReducer'
import { htsTableCols } from './htsTableCols'

interface ITableProps {
  type: string,
  stdUnit: string,
  monetaryUnit: string
}

function HtsTable(props: ITableProps) {
  const { type, stdUnit, monetaryUnit } = props
  const { htsData } = useSelector((state: ReducerState) => ({ htsData: state.home.htsData }))
  const dispatch = useDispatch()
  const [data, setData] = useState(htsData)

  return (
    <div style={{ height: '215px' }}>
      <PerfectScrollbar options={{ wheelPropagation: false }}>
        <Table
          className='customTable'
          columns={htsTableCols(type, stdUnit, monetaryUnit, data, setData, dispatch, htsData)}
          dataSource={data[type]}
          size='small'
          pagination={false}
        />
      </PerfectScrollbar>
    </div>
  )
}

export default HtsTable
