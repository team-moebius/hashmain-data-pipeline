// This is a legacy code, so will be delete
// Now is just for reference code about antd 'Form'.

import * as React from 'react'
import { FormComponentProps } from 'antd/lib/form/Form'
import { Form, Input, Badge } from 'antd'
// import { idCheck, nameCheck, passwordCheck, passwordConfirmCheck } from './formCheck'

function renderTextArea(): React.ReactElement {
  return (
    <>
      <div style={{ marginTop: '20px', textAlign: 'left', fontSize: '12px' }}>
        <Badge status='default' />
        <p className='emphasisText'>수신이 가능한 이메일 주소</p>
        를 입력하시기 바랍니다. 회원 가입 이후
        <p className='emphasisText'>계정 인증용 메일</p>
        이 전송됩니다.
      </div>
      <div style={{ marginTop: '15px', textAlign: 'left', fontSize: '12px' }}>
        <Badge status='default' />
        메일 전송은 60초 정도 소요될 수 있으며, 메일이 누락 될 경우에
        <p className='emphasisText'>스팸 메일함을 확인</p> 하시기 바랍니다.
      </div>
    </>
  )
}

function JoinForm({ form }: FormComponentProps): React.ReactElement {
  const { getFieldDecorator } = form
  return (
    <>
      <Form>
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: 'Please input your username!' }]
          })(
            <Input
              placeholder='E-Mail (User ID)'
            />
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: 'Please input your username!' }]
          })(
            <Input
              placeholder='User Name'
            />
          )}
        </Form.Item>
        {renderTextArea()}
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: 'Please input your username!' }]
          })(
            <Input
              placeholder='Password (영문 숫자 포함 8자 이상)'
            />
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: 'Please input your username!' }]
          })(
            <Input
              placeholder='Password Confirm'
            />
          )}
        </Form.Item>
      </Form>
    </>
  )
}

export default Form.create<FormComponentProps>()(JoinForm)
