import React from 'react'
import { useSelector } from 'react-redux'
import { Table, Card } from 'antd'

import { manageCols } from './manageCols'
import { ReducerState } from '../../../reducers/rootReducer'

function ManageTable() {
  const { manageData, dataLoading } = useSelector((state: ReducerState) => ({
    manageData: state.hts.manageData,
    dataLoading: state.common.loading['HTS_TRADE_INFO']
  }))

  return (
    <Card className='manageTableInHts' loading={dataLoading}>
      <Table
        className='customTable'
        columns={manageCols()}
        dataSource={manageData}
        size='small'
        pagination={false}
        scroll={{ y: 230 }}
      />
    </Card>
  )
}

export default ManageTable
