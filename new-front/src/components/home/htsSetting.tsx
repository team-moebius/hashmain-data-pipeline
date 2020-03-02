import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Tabs, Card, Spin } from 'antd'

import HtsTable from './hts/htsTable'
import RightSection from './hts/rightSection'
import { ReducerState } from '../../reducers/rootReducer'
import { HTS_TRADE_INFO_REQUESTED } from '../../actions/commands/homeActionCommand'
import { htsInfoActionType } from '../../actions/homeAction'

function HTSBody(dataLoading: boolean, assets: string) {
  const stdCoin = '.KRW'
  return (
    <div className='backgroundColor' style={{ height: '100%', marginTop: '6px' }}>
      <Spin spinning={dataLoading}>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='first' stdCoin={stdCoin} assets={assets} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='second' stdCoin={stdCoin} assets={assets} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='third' stdCoin={stdCoin} assets={assets} />
        </Card>
      </Spin>
    </div>
  )
}

function HTSSetting() {
  const [selectedTab, setSelectedTab] = useState('trade')
  const dispatch = useDispatch()
  const { dataLoading } = useSelector((state: ReducerState) => (
    { dataLoading: state.common.loading['HTS_TRADE_INFO'] }
  ))
  const assets = '.XRP'
  useEffect(() => {
    dispatch(htsInfoActionType({ type: HTS_TRADE_INFO_REQUESTED, menuType: selectedTab }))
  }, [dispatch, selectedTab])

  return (
    <>
      <div style={{ display: 'inline-block', width: 'calc(93% - 550px)', marginLeft: '10px', height: '100%' }}>
        <Tabs
          className='leftTabs backgroundColor contentsHeader'
          defaultActiveKey={selectedTab}
          onChange={(key) => { setSelectedTab(key) }}
        ><Tabs.TabPane tab={<p style={{ margin: 0, fontWeight: 600 }}>Tab 1</p>} key='trade' /></Tabs>
        {HTSBody(dataLoading, assets)}
      </div>
      <RightSection />
    </>
  )
}

export default HTSSetting
