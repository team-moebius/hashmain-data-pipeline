import React from 'react'
import { Button, Result } from 'antd'
import { useCustomRouter } from '../common/router/routerPush'

function NotFound() {
  const router = useCustomRouter()
  return (
    <Result
      style={{ position: 'relative', top: '30%' }}
      status='error'
      title='해당 페이지는 존재하지 않습니다.'
      extra={[
        <Button type='primary' key='home' onClick={() => router.push('/')}>
          홈으로 돌아가기
        </Button>
      ]}
    />
  )
}

export default NotFound
