import React from 'react'
import { Button } from 'antd'
import { useCustomRouter } from '../../common/router/routerPush'


function HeaderBar() {
  const rounter = useCustomRouter()

  return (
    <div className='headerBar backgroundColor'>
      <Button onClick={() => {
        rounter.push('/sign')
        window.localStorage.clear()
        window.location.reload()
      }}>로그아웃</Button>
      header
    </div>
  )
}

export default HeaderBar
