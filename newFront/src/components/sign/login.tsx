import * as React from 'react'
import { Input, Button, Icon, Badge } from 'antd'

function renderTextArea (): React.ReactElement {
  return <>
    <div style={{ marginTop: '20px', textAlign: 'left', fontSize: '12px' }}>
      <Badge status='default'/>
      로그인 후 CRYPYO BOX 의 서비스 이용 시
      <p className='emphasisText'>이용약관</p> 및
      <p className='emphasisText'>개인</p>
      <p className='emphasisText'>정보</p>
      <p className='emphasisText'>정책</p>
      에 동의하는 것으로 간주합니다.
    </div>
    <div style={{ marginTop: '15px', textAlign: 'left', fontSize: '12px' }}>
      <Badge status='default'/>
      CRYPTO BOX 는
      <p className='emphasisText'>모든 브라우저에 최적화</p> 되었습니다.
    </div>
  </>
}

function Login({}) {
  return <>
    <Input
      style={{ marginTop: '10px' }}
      placeholder='E-Mail'
      prefix={<Icon type='mail' style={{ marginRight: '5px' }} />}
    />
    <Input.Password
      style={{ marginTop: '10px' }}
      placeholder='Password'
      prefix={<Icon type='lock' style={{ marginRight: '10px'}} />}
    />
    <Button
      className='customBtn'
      type='primary'
      style={{ marginTop: '15px', width: '100%' }}
      onClick={() => console.log('click login')}>로그인</Button>
    {renderTextArea()}
  </>

}

export default Login
