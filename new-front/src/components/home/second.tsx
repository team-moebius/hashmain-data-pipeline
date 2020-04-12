import React from 'react'
import { Result } from 'antd'

function Second() {
  return (
    <div className='contents backgroundColor'>
      <Result
        style={{ top: '400px', position: 'relative' }}
        status='warning'
        title='서비스 준비중입니다.'
        // subTitle='곧 업데이트 될 예정입니다.'
        // extra={<Button type="primary">Back Home</Button>}
      />
    </div>
  )
}

export default Second
