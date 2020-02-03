import * as React from 'react'
import { useState } from 'react'
import { Button, Checkbox, Badge, Input } from 'antd'
import { mailCheck, nameCheck, passwordCheck, passwordConfirmCheck } from './formCheck'

enum boxType {
  Mail = 0, 
  Name = 1,
  Pass = 2,
  PassCnf = 3
}

function ruleCheck (type: number, value: string, pwdCnf=''): boolean {
  switch(type) {
    case boxType.Mail: return mailCheck(value)
    case boxType.Pass: return passwordCheck(value)
    case boxType.PassCnf: return passwordConfirmCheck(value, pwdCnf)
    default: return nameCheck(value)
  }
}

function RenderInputBox (type: number, inputValue: Array<string>): React.ReactElement {
  const placeholders = ['E-Mail (User ID)', 'User Name', 'Password (영문 숫자 포함 8자 이상)', 'Password Confirm']
  const errorMsg = ['ID를 E-mail 형태로 입력해주세요.', '이름을 입력 해주세요', '패스워드는 영문, 숫자 포함 8자 이상 30자 이하여야 합니다.', '패스워드가 일치하지 않습니다.']
  const [isLegal, setIsLegal] = useState(true)

  if (type < 2) {
    return <div>
      <Input
        style={{ marginTop: '10px' }}
        placeholder={placeholders[type]}
        onChange={e => inputValue[type] = (e.target.value)}
        onBlur={e => setIsLegal(ruleCheck(type, inputValue[type]))} />
      {!isLegal && <p className='errorText'>{errorMsg[type]}</p>}
    </div>
  } else {
    return <div>
      <Input.Password
        style={{ marginTop: '10px' }}
        placeholder={placeholders[type]}
        onChange={e => inputValue[type] = (e.target.value)}
        onBlur={(e) => setIsLegal(ruleCheck(type, inputValue[type], inputValue[type - 1]))}/>
      {!isLegal && <p className='errorText'>{errorMsg[type]}</p>}
    </div>
  }
}

function renderTextArea (): React.ReactElement {
  return <>
    <div style={{ marginTop: '20px', textAlign: 'left', fontSize: '12px' }}>
      <Badge status='default'/>
      <p className='emphasisText'>수신이 가능한 이메일 주소</p>
      를 입력하시기 바랍니다. 회원 가입 이후 
      <p className='emphasisText'>계정 인증용 메일</p>
      이 전송됩니다.
    </div>
    <div style={{ marginTop: '15px', textAlign: 'left', fontSize: '12px' }}>
      <Badge status='default'/>
      메일 전송은 60초 정도 소요될 수 있으며, 메일이 누락 될 경우에
      <p className='emphasisText'>스팸 메일함을 확인</p> 하시기 바랍니다.
    </div>
  </>
}

function Join() {
  // 이거 스토어로 관리해야함!!
  const inputValue = ['', '', '', '']

  return <>
    {RenderInputBox(boxType.Mail, inputValue)}
    {RenderInputBox(boxType.Name, inputValue)}
    {renderTextArea()}
    {RenderInputBox(boxType.Pass, inputValue)}
    {RenderInputBox(boxType.PassCnf, inputValue)}
    <Checkbox style={{ float: 'left', marginTop: '15px' }}><>
      <p className='emphasisText'>이용약관</p> 및
      <p className='emphasisText'>개인 정보 정책</p>에 동의합니다.
    </></Checkbox>
    <Button className='customBtn' style={{ marginTop: '15px', width: '100%' }} type='primary'>회원가입</Button>
  </>
}

export default Join
