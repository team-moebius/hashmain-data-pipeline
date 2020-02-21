import React, { useEffect } from 'react'
import { Input, Button, Icon, Badge } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { signInAction, signResetAction } from '../../actions/signAction'
import { ReducerState } from '../../reducers/rootReducer'
import { SIGN_IN_REQUESTED, SIGN_REDUCER_RESET } from '../../actionCmds/signActionCmd'
import { signInFailedFunc } from '../sign/signFuntions'
import { useCustomRouter } from '../../common/router/routerPush'

function renderTextArea(): React.ReactElement {
  return (
    <>
      <div style={{ marginTop: '20px', textAlign: 'left', fontSize: '12px' }}>
        <Badge status='default' />
        로그인 후 CRYPYO BOX 의 서비스 이용 시
        <p className='emphasisText'>이용약관</p> 및
        <p className='emphasisText'>개인</p>
        <p className='emphasisText'>정보</p>
        <p className='emphasisText'>정책</p>
        에 동의하는 것으로 간주합니다.
      </div>
      <div style={{ marginTop: '15px', textAlign: 'left', fontSize: '12px' }}>
        <Badge status='default' />
        CRYPTO BOX 는
        <p className='emphasisText'>모든 브라우저에 최적화</p> 되었습니다.
      </div>
    </>
  )
}

function Login() {
  const inputValue = { mail: '', pwd: '' }
  const dispatch = useDispatch()
  const { signInFailed, token } = useSelector((state: ReducerState) => (
    { signInFailed: state.sign.loginFailed, token: state.common.token }
  ))
  const router = useCustomRouter()

  useEffect(() => {
    if (signInFailed) { signInFailedFunc(dispatch) }
    if (token) { router.push('/') }
  }, [dispatch, signInFailed, token, router])

  return (
    <>
      <Input
        style={{ marginTop: '10px', textAlign: 'left' }}
        placeholder='E-Mail'
        onChange={(e) => { inputValue.mail = e.target.value }}
        prefix={<Icon type='mail' style={{ marginRight: '5px' }} />}
      />
      <Input.Password
        style={{ marginTop: '10px', textAlign: 'left' }}
        placeholder='Password'
        onChange={(e) => { inputValue.pwd = e.target.value }}
        prefix={<Icon type='lock' style={{ marginRight: '10px' }} />}
      />
      <Button
        className='customBtn'
        type='primary'
        style={{ marginTop: '15px', width: '100%' }}
        onClick={() => {
          dispatch(signInAction({ type: SIGN_IN_REQUESTED, mail: inputValue.mail, pwd: inputValue.pwd }))
          dispatch(signResetAction({ type: SIGN_REDUCER_RESET }))
        }}>로그인</Button>
      {renderTextArea()}
    </>
  )
}

export default Login
