import { mailAction, nameAction, pwdAction, pwdChkAction, signUpAction } from '../../actions/signAction'
import {
  MAIL_VALUE_CHANGE_REQUESTED,
  NAME_VALUE_CHANGE_REQUESTED,
  PWD_VALUE_CHANGE_REQUESTED,
  PWD_CHECK_VALUE_CHANGE_REQUESTED,
  SIGN_UP_REQUESTED,
  SIGN_UP_RESET,
  SIGN_IN_FAILED
} from '../../actionCmds/signActionCmd'
import { openNotification } from '../../common/common'

function mailCheck(id: string): boolean {
  // eslint-disable-next-line no-useless-escape
  const regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i
  return regExp.test(id)
}

function nameCheck(name: string): boolean {
  return name.length > 0
}

function passwordCheck(password: string): boolean {
  const regExp = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,30}$/
  return regExp.test(password)
}

function passwordConfirmCheck(password: string, confirmPassword: string): boolean {
  if (!password || !confirmPassword) return false
  return password === confirmPassword
}

export function ruleCheck(dispatch: any, type: number, value: string, pwdCnf = ''): boolean {
  let isLegal = false
  switch (type) {
    case 0:
      isLegal = mailCheck(value)
      if (isLegal) { dispatch(mailAction({ type: MAIL_VALUE_CHANGE_REQUESTED, mail: value })) }
      break
    case 1:
      isLegal = nameCheck(value)
      if (isLegal) { dispatch(nameAction({ type: NAME_VALUE_CHANGE_REQUESTED, name: value })) }
      break
    case 2:
      isLegal = passwordCheck(value)
      if (isLegal) { dispatch(pwdAction({ type: PWD_VALUE_CHANGE_REQUESTED, pwd: value })) }
      break
    default:
      isLegal = passwordConfirmCheck(value, pwdCnf)
      if (isLegal) { dispatch(pwdChkAction({ type: PWD_CHECK_VALUE_CHANGE_REQUESTED, pwdChk: value })) }
      break
  }
  return isLegal
}

export function signUpClick(
  dispatch: any, email: string, userName: string, password: string, pwdCheck: string, idExist: boolean
): void {
  if (!email || !userName || !password || !pwdCheck || idExist
    || !mailCheck(email) || !nameCheck(userName)
    || !passwordCheck(password) || !passwordConfirmCheck(password, pwdCheck)) {
    openNotification('error', '입력을 확인해주세요.')
    return
  }
  dispatch(signUpAction({ type: SIGN_UP_REQUESTED, mail: email, name: userName, pwd: password }))
}

export function signupFailed(dispatch: any) {
  openNotification('error', '잠시 후 다시 시도해주세요.')
  dispatch({ type: SIGN_UP_RESET })
}

export function signInFailedFunc(dispatch: any) {
  openNotification('error', 'ID/PW를 확인해주세요.')
  dispatch({ type: SIGN_IN_FAILED, msg: '' })
}
