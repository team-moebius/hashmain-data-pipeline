import React from 'react'
import { useDispatch } from 'react-redux'
import { Result, Button } from 'antd'
import { useCustomRouter } from '../../common/router/routerPush'
import { signResetAction } from '../../actions/signAction'
import { SIGN_REDUCER_RESET } from '../../actions/commands/signActionCommand'

function ResultView() {
  const router = useCustomRouter()
  const dispatch = useDispatch()

  return (
    <>
      <Result
        className='reusltView'
        status='success'
        title='회원가입이 완료되었습니다. 인증을 완료해주세요.'
        subTitle='메일 전송은 60초 정도 소요될 수 있습니다.'
        extra={[
          <Button type='primary' key='console' onClick={() => {
            router.push('/')
            dispatch(signResetAction({ type: SIGN_REDUCER_RESET }))
          }}>
            로그인 화면으로 이동
          </Button>
        ]}
      />
    </>
  )
}

export default ResultView
