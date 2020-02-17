import {
  LOGIN_STATE_CHANGE_REQUESTED
} from '../actionCmds/homeActionCmd'

interface ILoginStateActionType { type: typeof LOGIN_STATE_CHANGE_REQUESTED, isLogin: string }
export const MailAction = (param: ILoginStateActionType): ILoginStateActionType => (
  { type: param.type, isLogin: param.isLogin }
)
