import React from 'react'
import { useSelector } from 'react-redux'
import { Table, Card } from 'antd'
import PerfectScrollbar from 'react-perfect-scrollbar'

import { manageCols } from './manageCols'
import { ReducerState } from '../../../reducers/rootReducer'

function ManageTable() {
  const { manageData, dataLoading } = useSelector((state: ReducerState) => ({
    manageData: state.home.manageData,
    dataLoading: state.common.loading['HTS_TRADE_INFO']
  }))

  return (
    <Card className='manageTableInHts' loading={dataLoading}>
      <PerfectScrollbar options={{ wheelPropagation: false }}>
        <Table
          className='customTable'
          columns={manageCols()}
          dataSource={manageData}
          size='small'
          pagination={false}
        />
      </PerfectScrollbar>
    </Card>
  )
}

export default ManageTable
