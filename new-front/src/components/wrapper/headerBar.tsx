import React from 'react'
import { Button } from 'antd'
import { useCustomRouter } from '../../common/router/routerPush'
import headerLogo from '../../svg/headerLogo.svg'

function HeaderBar() {
  const rounter = useCustomRouter()

  return (
    <div className='headerBar backgroundColor' style={{ padding: '7px 20px' }}>
      <img src={headerLogo} alt='' style={{ height: '43px', width: '40px', marginRight: '20px' }} />
      <p style={{ fontSize: '22px', fontWeight: 'bold', display: 'inline-block', verticalAlign: 'middle' }}>
        H<span style={{ color: '#ff3a7d' }}>Λ</span>SHM<span style={{ color: '#ff3a7d' }}>Λ</span>IN. Pro
      </p>
      <Button
        style={{
          float: 'right',
          border: '2px solid #ff3a7d',
          margin: '10px'
        }}
        onClick={() => {
          rounter.push('/sign')
          window.localStorage.clear()
          window.location.reload()
        }}>로그아웃</Button>
    </div>
  )
}

export default HeaderBar
