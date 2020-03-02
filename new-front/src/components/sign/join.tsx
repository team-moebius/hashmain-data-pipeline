import React, { useState, useEffect } from 'react'
import { useSelector, useDispatch } from 'react-redux'
import { Button, Checkbox, Badge, Input } from 'antd'
import { ReducerState } from '../../reducers/rootReducer'
import { ruleCheck, signUpClick, signupFailed } from './signFuntions'
import { openNotification } from '../../common/common'

enum boxType {
  Mail = 0,
  Name = 1,
  Pass = 2,
  PassCnf = 3
}

function RenderInputBox(type: number): React.ReactElement {
  const dispatch = useDispatch()
  const { mail, name, pwdChk, pwd, idExist } = useSelector((state: ReducerState) => getState(state))
  const [isLegal, setIsLegal] = useState(true)
  const placeholders = ['E-Mail (User ID)', 'User Name', 'Password (영문 숫자 포함 8자 이상)', 'Password Confirm']
  const errorMsg = ['ID를 E-mail 형태로 입력해주세요.', '이름을 입력 해주세요', '패스워드는 영문, 숫자 포함 8자 이상 30자 이하여야 합니다.', '패스워드가 일치하지 않습니다.']
  const defaultValue = [mail, name, pwd, pwdChk]
  let inputValue = defaultValue[type]

  if (type < 2) {
    return (
      <div style={{ textAlign: 'left' }}>
        <Input
          style={{ marginTop: '10px' }}
          placeholder={placeholders[type]}
          defaultValue={defaultValue[type]}
          onChange={(e) => { inputValue = (e.target.value) }}
          onBlur={() => setIsLegal(ruleCheck(dispatch, type, inputValue))} />
        {!isLegal && <p className='errorText'>{errorMsg[type]}</p>}
        {(type === boxType.Mail && idExist) && <p className='errorText'>이미 존재하는 ID입니다.</p>}
      </div>
    )
  }

  return (
    <div style={{ textAlign: 'left' }}>
      <Input.Password
        style={{ marginTop: '10px' }}
        placeholder={placeholders[type]}
        defaultValue={defaultValue[type]}
        onChange={(e) => { inputValue = (e.target.value) }}
        onBlur={() => setIsLegal(ruleCheck(dispatch, type, inputValue, pwd))} />
      {!isLegal && <p className='errorText'>{errorMsg[type]}</p>}
    </div>
  )
}

function RenderTextArea(): React.ReactElement {
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

function Join() {
  const dispatch = useDispatch()
  const {
    mail, name, pwdChk, pwd, idExist, signUpFailed
  } = useSelector((state: ReducerState) => getState(state))
  const [isChecked, setIsChecked] = useState(false)

  useEffect(() => {
    if (signUpFailed) { signupFailed(dispatch) }
  }, [dispatch, signUpFailed])

  return (
    <>
      {RenderInputBox(boxType.Mail)}
      {RenderTextArea()}
      {RenderInputBox(boxType.Name)}
      {RenderInputBox(boxType.Pass)}
      {RenderInputBox(boxType.PassCnf)}
      <Checkbox
        className='customCheckBox'
        style={{ float: 'left', marginTop: '15px' }}
        onChange={(e) => setIsChecked(e.target.checked)}
      >
        <>
          <p className='emphasisText'>이용약관</p> 및
          <p className='emphasisText'>개인 정보 정책</p>에 동의합니다.
        </>
      </Checkbox>
      <Button className='customBtn' style={{ marginTop: '15px', width: '100%' }} type='primary' onClick={() => {
        isChecked ? signUpClick(dispatch, mail, name, pwd, pwdChk, idExist)
          : openNotification('error', '개인정보 약관에 동의해주세요.')
      }}>회원가입</Button>
    </>
  )
}

export default Join

function getState(state: ReducerState) {
  const { sign, common } = state
  return ({
    mail: sign.mail,
    pwd: state.sign.pwd,
    name: sign.name,
    pwdChk: sign.pwdChk,
    idExist: sign.idExist,
    signUpFailed: common.failed['SIGN_UP']
  })
}
