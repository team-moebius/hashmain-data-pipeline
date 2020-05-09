import React from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { Button, Icon, Input } from 'antd'
import { apiKeyRegister } from './apiKeyRegisterFunc'
import { ReducerState } from '../../../reducers/rootReducer'
import apiSvg from '../../../svg/api.svg'

interface IAPIKeyRegistProps {
  isRegister: boolean
  setIsRegister: any
}

function renderRegisterKey(
  setIsRegister: any,
  isValid: boolean,
  dispatch: any,
  registerValue: any,
  exchange: string
) {
  const inputValue = { name: '', accessKey: '', secretKey: '' }

  return (
    <div style={{ padding: '10px', overflow: 'hidden', height: 'inherit' }}>
      <p style={{ fontWeight: 'bold', textAlign: 'center', marginBottom: '10px' }}>API KEY 등록하기</p>
      <Input
        style={{ marginBottom: '5px' }}
        placeholder='Bot Name'
        onChange={(e) => { inputValue.name = e.target.value }} />
      <Input
        style={{ marginBottom: '5px' }}
        placeholder='Access Key'
        onChange={(e) => { inputValue.accessKey = e.target.value }} />
      <Input
        style={{ marginBottom: '20px' }}
        placeholder='Secrat Key'
        onChange={(e) => { inputValue.secretKey = e.target.value }} />
      <ul style={{ paddingLeft: '20px' }}>
        <li>API KEY 및 API SECRET KEY 관리에 유의하시기 바랍니다.</li><br />
        <li>회원의 관리 소홀로 발생한 손실 및 유실에 대하여 ICORE. Inc는 책임지지 않습니다.</li><br />
        <li>API KEY를 생성할 때는 꼭 자산 출금 권한 체크를 해제 하였는지 확인 하시기 바랍니다.</li><br />
        <li>HASHMAIN System은 회원이 설정한 주문에 대해서 발동 할 수 있도록 24시간 자동 매매 서비스를 제공합니다.</li><br />
        <li>매매 포지션을 설정하는 것은 전적으로 회원의 선택에 따른 것으로서 가상화폐 시세 하락으로 거래 결과가
          손해로 이어질 수 있으며, ICORE. Inc는 이에 대하여 책임을 지지 않습니다.</li><br />
      </ul>
      <Button
        type='primary'
        style={{ width: '49%' }}
        onClick={() => apiKeyRegister(registerValue, dispatch, isValid, setIsRegister, exchange)}>등록하기</Button>
      <Button type='primary' style={{ width: '49%', float: 'right' }} onClick={() => setIsRegister(false)}>돌아가기</Button>
    </div>
  )
}

function renderDisplayKey(setIsRegister: any) {
  return (
    <div style={{ textAlign: 'center', padding: '10px' }}>
      <img src={apiSvg} alt='' style={{ width: '75px', height: '65px', marginTop: '10px' }} />
      <p style={{ marginTop: '10px' }}>API KEY를 등록하시기 바랍니다.</p>
      <p style={{ marginBottom: '10px' }}>{'( 펼쳐보기 > KEY 등록 > 유효성 검사 > 등록완료 )'}</p>
      <Button type='link' onClick={() => { setIsRegister(true) }}><Icon type='caret-down' /> 펼쳐보기</Button>
    </div>
  )
}

function ApiKeyRegister(props: IAPIKeyRegistProps) {
  const { isRegister, setIsRegister } = props
  const dispatch = useDispatch()
  const { registerValue, isValid, exchange } = useSelector((state: ReducerState) => ({
    registerValue: state.hts.registerValue,
    isValid: state.hts.isValid,
    exchange: state.hts.exchange
  }))
  return (
    <div
      className='backgroundColor'
      style={{ height: isRegister ? '640px' : '190px', transition: 'height .15s linear' }}
    >
      {isRegister
        ? renderRegisterKey(setIsRegister, isValid, dispatch, registerValue, exchange)
        : renderDisplayKey(setIsRegister)}
    </div>
  )
}

export default ApiKeyRegister
