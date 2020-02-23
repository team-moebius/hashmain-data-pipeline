import {
  MAIL_VALUE_CHANGE_REQUESTED,
  NAME_VALUE_CHANGE_REQUESTED,
  PWD_VALUE_CHANGE_REQUESTED,
  PWD_CHECK_VALUE_CHANGE_REQUESTED,
  SIGN_UP_REQUESTED,
  SIGN_UP_SUCCESS,
  SIGN_UP_FAILED,
  MAIL_DUPLICATION_CHECK_SUCCESS,
  MAIL_DUPLICATION_CHECK_FAILED,
  SIGN_IN_REQUESTED,
  SIGN_IN_SUCCESS,
  SIGN_IN_FAILED,
  SIGN_REDUCER_RESET
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

interface ISignUpActionType { type: typeof SIGN_UP_REQUESTED, mail: string, name: string, pwd: string }
export const signUpAction = (param: ISignUpActionType): ISignUpActionType => (
  { type: param.type, mail: param.mail, name: param.name, pwd: param.pwd }
)

interface ISignUpSuccessActionType { type: typeof SIGN_UP_SUCCESS, signDone: boolean }
export const signUpSuccessAction = (param: ISignUpSuccessActionType): ISignUpSuccessActionType => (
  { type: param.type, signDone: param.signDone }
)

interface ISignUpFailedActionType { type: typeof SIGN_UP_FAILED, msg: string }
export const signUpFailedAction = (param: ISignUpFailedActionType): ISignUpFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface IMailDupSuccessActionType { type: typeof MAIL_DUPLICATION_CHECK_SUCCESS, idExist: boolean }
export const mailDupSuccessAction = (param: IMailDupSuccessActionType): IMailDupSuccessActionType => (
  { type: param.type, idExist: param.idExist }
)

interface IMailDupFailedActionType { type: typeof MAIL_DUPLICATION_CHECK_FAILED, msg: string }
export const mailDupFailedAction = (param: IMailDupFailedActionType): IMailDupFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface ISignInActionType { type: typeof SIGN_IN_REQUESTED, mail: string, pwd: string }
export const signInAction = (param: ISignInActionType): ISignInActionType => (
  { type: param.type, mail: param.mail, pwd: param.pwd }
)

interface ISignInSuccessActionType { type: typeof SIGN_IN_SUCCESS, token: string }
export const signInSuccessAction = (param: ISignInSuccessActionType): ISignInSuccessActionType => (
  { type: param.type, token: param.token }
)

interface ISignInFailedActionType { type: typeof SIGN_IN_FAILED, msg: string }
export const signInFailedAction = (param: ISignInFailedActionType): ISignInFailedActionType => (
  { type: param.type, msg: param.msg }
)

interface ISignResetActionType { type: typeof SIGN_REDUCER_RESET }
export const signResetAction = (param: ISignResetActionType): ISignResetActionType => (
  { type: param.type }
)

export type singActionTypes = IMailActionType | INameActionType | IPwdActionType | IPwdChkActionType |
  ISignUpActionType | ISignUpFailedActionType | ISignUpSuccessActionType |
  IMailDupSuccessActionType | IMailDupFailedActionType | ISignInActionType |
  ISignInSuccessActionType | ISignInFailedActionType | ISignResetActionType
