import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Table } from 'antd'

import { ReducerState } from '../../../reducers/rootReducer'
import { htsTableCols } from './htsTableCols'
import { purchaseRes } from './purchaseRes'

interface ITableProps {
  type: string,
  stdUnit: string,
  monetaryUnit: string,
  tableData: any,
  setTableData: any
}

function renderFooter(type: string, tableData: any) {
  if (type === 'purchase') {
    return purchaseRes(type, tableData)
  }
  return <div style={{ height: '0' }} />
}

function HtsTable(props: ITableProps) {
  const { type, stdUnit, monetaryUnit, tableData, setTableData } = props
  const dispatch = useDispatch()
  const { assetsData, exchange } = useSelector((state: ReducerState) => ({
    assetsData: state.hts.assetsData,
    exchange: state.hts.exchange
  }))

  return (
    <div style={{ height: type === 'purchase' ? '255px' : '215px' }}>
      <Table
        className='customTable'
        columns={htsTableCols(type, stdUnit, monetaryUnit, tableData, setTableData, dispatch, assetsData, exchange)}
        dataSource={tableData[type]}
        size='small'
        rowKey={(record: any, idx: number) => `${type}_${idx}`}
        pagination={false}
        scroll={{ y: 200 }}
        footer={() => renderFooter(type, tableData)}
      />
    </div>
  )
}

export default HtsTable
