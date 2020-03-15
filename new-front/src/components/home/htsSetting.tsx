import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Tabs, Card, Spin, Button } from 'antd'

import HtsTable from './hts/htsTable'
import RightSection from './hts/rightSection'
import ManageTable from './manage/manageTable'
import { ReducerState } from '../../reducers/rootReducer'
import { HTS_TRADE_INFO_REQUESTED, HTS_TRADE_ORDER_REQUESTED } from '../../actionCmds/htsActionCmd'
import { htsInfoActionType, htsOrderActionType } from '../../actions/htsAction'
import { orderRegisterCheck } from './hts/rules'
import { openNotification } from '../../common/common'
import '../../style/hts.css'

function buildDtos(htsData: any): Array<Object> {
  return [].concat(htsData.sale).concat(htsData.purchase).concat(htsData.stopLoss)
    .map((elm: any) => {
      if (elm.eventType === 'READ') { return { ...elm, eventType: 'UPDATE' } }
      return elm
    })
}
function renderErrorMsg(erros : {
  descPurchase: boolean, descSale: boolean, descStopLoss: boolean,
  stopLossErr: boolean, saleErr: boolean, purchaseErr: boolean
}): boolean {
  openNotification('error', '다음과 같은 이유로 등록 할 수 없습니다.', (
    <div style={{ padding: '5px', fontSize: '12px' }}>
      <p>{erros.descPurchase && '* 이익실현은 N-1차 보다 낮은 가격으로 설정하셔야 합니다.'}</p>
      <p>{erros.descSale && '* 매수는 N-1차 보다 높은 가격으로 설정하셔야 합니다.'}</p>
      <p>{erros.descStopLoss && '* 감시지정은 N-1차 보다 낮은 가격으로 설정하셔야 합니다.'}</p>
      <p>{erros.stopLossErr && '* 감시 지정가는 최저 매수가 이하로 설정하셔야 합니다.'}</p>
      <p>{erros.saleErr && '* 매도가는 최고 매수가 이상으로 설정하셔야 합니다.'}</p>
      <p>{erros.purchaseErr && '* 자산이 부족합니다.'}</p>
    </div>
  ))
  return !erros.descPurchase && !erros.descSale && !erros.descStopLoss
  && !erros.stopLossErr && !erros.saleErr && !erros.purchaseErr
}

function HTSBody(dataLoading: boolean, monetaryUnit: string, dispatch: any, htsData: any) {
  const stdUnit = '.KRW'
  return (
    <div className='backgroundColor' style={{ marginTop: '6px', opacity: 100 }}>
      <Spin spinning={dataLoading}>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='sale' stdUnit={stdUnit} monetaryUnit={monetaryUnit} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='purchase' stdUnit={stdUnit} monetaryUnit={monetaryUnit} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='stopLoss' stdUnit={stdUnit} monetaryUnit={monetaryUnit} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <Button
            style={{ width: '100%', backgroundColor: '#0A0A28' }}
            onClick={() => {
              if (renderErrorMsg(orderRegisterCheck(htsData, stdUnit.slice(1)))) {
                dispatch(htsOrderActionType({ type: HTS_TRADE_ORDER_REQUESTED, dtos: buildDtos(htsData) }))
              }
            }}
          >api call</Button>
        </Card>
      </Spin>
    </div>
  )
}

function HTSSetting() {
  const [selectedTab, setSelectedTab] = useState('trade')
  const dispatch = useDispatch()
  const { dataLoading, htsData } = useSelector((state: ReducerState) => ({
    dataLoading: state.common.loading['HTS_TRADE_INFO'],
    htsData: state.home.htsData
  }))
  const monetaryUnit = '.XRP'
  useEffect(() => {
    dispatch(htsInfoActionType({ type: HTS_TRADE_INFO_REQUESTED, menuType: selectedTab }))
  }, [dispatch, selectedTab])

  return (
    <>
      <div style={{ display: 'inline-block', width: '920px', marginLeft: '10px', height: '915px' }}>
        <Tabs
          className='leftTabs backgroundColor contentsHeader'
          defaultActiveKey={selectedTab}
          onChange={(key) => { setSelectedTab(key) }}
        ><Tabs.TabPane tab={<p style={{ margin: 0, fontWeight: 600 }}>Tab 1</p>} key='trade' /></Tabs>
        {HTSBody(dataLoading, monetaryUnit, dispatch, htsData)}
      </div>
      <RightSection />
      <ManageTable />
    </>
  )
}

export default HTSSetting
