import {
  MAIL_VALUE_CHANGE_REQUESTED,
  NAME_VALUE_CHANGE_REQUESTED,
  PWD_VALUE_CHANGE_REQUESTED,
  PWD_CHECK_VALUE_CHANGE_REQUESTED,
  SIGN_UP_REQUESTED,
  SIGN_UP_SUCCESS,
  SIGN_UP_FAILED
} from '../actionCmds/signActionCmd'

interface IMailActionType { type: typeof MAIL_VALUE_CHANGE_REQUESTED, mail: string }
export const mailAction = (param: IMailActionType): IMailActionType => (
  { type: param.type, mail: param.mail }
)

interface INameActionType { type: typeof NAME_VALUE_CHANGE_REQUESTED, name: string }
export const nameAction = (param: INameActionType): INameActionType => (
  { type: param.type, name: param.name }
)

interface IPwdActionType { type: typeof PWD_VALUE_CHANGE_REQUESTED, pwd: string }
export const pwdAction = (param: IPwdActionType): IPwdActionType => (
  { type: param.type, pwd: param.pwd }
)

interface IPwdChkActionType { type: typeof PWD_CHECK_VALUE_CHANGE_REQUESTED, pwdChk: string }
export const pwdChkAction = (param: IPwdChkActionType): IPwdChkActionType => (
  { type: param.type, pwdChk: param.pwdChk }
)

interface ISignUpActionType { type: typeof SIGN_UP_REQUESTED, mail: string, name: string, pwd: string, pwdChk: string }
export const signUpAction = (param: ISignUpActionType): ISignUpActionType => (
  { type: param.type, mail: param.mail, name: param.name, pwd: param.pwd, pwdChk: param.pwdChk }
)

interface ISignUpSuccessActionType { type: typeof SIGN_UP_SUCCESS, msg: string }
export const signUpSuccessAction = (param: ISignUpSuccessActionType): ISignUpSuccessActionType => (
  { type: param.type, msg: param.msg }
)

interface ISignUpFailedActionType { type: typeof SIGN_UP_FAILED, msg: string }
export const signUpFailedAction = (param: ISignUpFailedActionType): ISignUpFailedActionType => (
  { type: param.type, msg: param.msg }
)

export type singActionTypes = IMailActionType | INameActionType | IPwdActionType | IPwdChkActionType |
  ISignUpActionType | ISignUpFailedActionType | ISignUpSuccessActionType
