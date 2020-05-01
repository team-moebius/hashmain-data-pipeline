import React, { useState, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Tabs, Card, Spin, Button } from 'antd'
import numeral from 'numeral'

import HtsTable from './hts/htsTable'
import RightSection from './hts/rightSection'
import ManageTable from './manage/manageTable'
import { ReducerState } from '../../reducers/rootReducer'
import {
  HTS_TRADE_INFO_REQUESTED, HTS_TRADE_ORDER_REQUESTED, HTS_ASSETS_REQUESTED, HTS_MANAGES_REQUESTED
} from '../../actionCmds/htsActionCmd'
import {
  htsInfoActionType, htsOrderActionType, htsAssetsActionType, htsManagesActionType
} from '../../actions/htsAction'
import { orderRegisterCheck } from './hts/rules'
import { HTSBtns } from './hts/htsStdButtons'
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
  const res = !erros.descPurchase && !erros.descSale && !erros.descStopLoss
  && !erros.stopLossErr && !erros.saleErr && !erros.purchaseErr

  if (!res) {
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
  }
  return res
}

function HTSBody(
  dataLoading: boolean, monetaryUnit: string, dispatch: any, stdUnit: string, data: any, setData: any, assetsData: any
) {
  return (
    <div className='backgroundColor' style={{ opacity: 1 }}>
      <Spin spinning={dataLoading}>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable type='sale' stdUnit={stdUnit} monetaryUnit={monetaryUnit} tableData={data} setTableData={setData} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable
            type='purchase' stdUnit={stdUnit} monetaryUnit={monetaryUnit} tableData={data} setTableData={setData} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <HtsTable
            type='stopLoss' stdUnit={stdUnit} monetaryUnit={monetaryUnit} tableData={data} setTableData={setData} />
        </Card>
        <Card style={{ display: 'inline-block', border: '0px solid white', width: '100%' }} loading={dataLoading}>
          <Button
            style={{ width: '100%', backgroundColor: '#0A0A28' }}
            onClick={() => {
              if (renderErrorMsg(orderRegisterCheck(data, stdUnit, assetsData))) {
                dispatch(htsOrderActionType({ type: HTS_TRADE_ORDER_REQUESTED, dtos: buildDtos(data) }))
              }
            }}
          >주문 등록</Button>
        </Card>
      </Spin>
    </div>
  )
}

function showAsset(assetsData: any, stdUnit: string, monetaryUnit: string) {
  let stdUnitAst = '0.0'
  let monetaryUnitAst = '0'

  if (assetsData) {
    assetsData.forEach((elm: any) => {
      if (elm.currency === stdUnit) { stdUnitAst = numeral(elm.balance).format(',') }
      if (elm.currency === monetaryUnit) { monetaryUnitAst = numeral(elm.balance).format(',') }
    })
  }
  return (
    <div
      className='backgroundColor showAsset'
      style={{ position: 'absolute' }}>
      <span style={{ marginRight: '20px' }}>{stdUnitAst && `${stdUnitAst} .${stdUnit}보유`}</span>
      <span>{monetaryUnitAst && `${monetaryUnitAst} .${monetaryUnit}보유`}</span>
    </div>
  )
}

function HTSSetting() {
  const [selectedTab, setSelectedTab] = useState('trade')
  const [stdUnit, setStdUnit] = useState('KRW')
  const dispatch = useDispatch()
  const { dataLoading, htsData, monetaryUnit, assetsData, nowExchage } = useSelector((state: ReducerState) => ({
    dataLoading: state.common.loading['HTS_TRADE_INFO'],
    htsData: state.hts.htsData,
    monetaryUnit: state.hts.monetaryUnit,
    assetsData: state.hts.assetsData,
    nowExchage: state.hts.exchange
  }))
  const [data, setData] = useState(htsData)

  useEffect(() => {
    if (Object.keys(htsData).length === 0) {
      dispatch(htsInfoActionType({ type: HTS_TRADE_INFO_REQUESTED, menuType: selectedTab }))
      dispatch(htsAssetsActionType({ type: HTS_ASSETS_REQUESTED, exchange: nowExchage }))
      dispatch(htsManagesActionType({ type: HTS_MANAGES_REQUESTED, exchange: nowExchage }))
    } else { setData(htsData) }
  }, [dispatch, selectedTab, htsData, nowExchage])

  return (
    <>
      <div style={{ display: 'inline-block', width: '920px', marginLeft: '10px', height: '1020px' }}>
        <Tabs
          className='leftTabs backgroundColor contentsHeader'
          defaultActiveKey={selectedTab}
          style={{ width: '645px', display: 'inline-block' }}
          onChange={(key) => { setSelectedTab(key) }}
        ><Tabs.TabPane tab={<p style={{ margin: 0, fontWeight: 600 }}>멀티거래 모드</p>} key='trade' /></Tabs>
        {showAsset(assetsData, stdUnit, monetaryUnit)}
        {HTSBtns(stdUnit, setStdUnit)}
        {HTSBody(dataLoading, monetaryUnit, dispatch, stdUnit, data, setData, assetsData)}
      </div>
      <RightSection />
      <ManageTable />
    </>
  )
}

export default HTSSetting
