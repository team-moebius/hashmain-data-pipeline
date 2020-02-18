import React from 'react'
import { Result, Button } from 'antd'

function ResultView() {
  return (
    <>
      <Result
        className='reusltView'
        status='success'
        title='회원가입이 완료되었습니다. 인증을 완료해주세요.'
        subTitle='메일 전송은 60초 정도 소요될 수 있습니다.'
        extra={[
          <Button type='primary' key='console'>
            <a href='/sign'>로그인 화면으로 이동</a>
          </Button>
        ]}
      />
    </>
  )
}

export default ResultView
