import {
  MENU_MODE_CHANGE_REQUESTED,
  HTS_TRADE_INFO_REQUESTED,
  HTS_TRADE_INFO_SUCCESS,
  HTS_TRADE_INFO_FAILED
} from './commands/homeActionCommand'

interface IMenuModeActionType { type: typeof MENU_MODE_CHANGE_REQUESTED, menuMode: string }
export const menuModeActionType = (param: IMenuModeActionType): IMenuModeActionType => (
  { type: param.type, menuMode: param.menuMode }
)

interface IHtsInfoActionType { type: typeof HTS_TRADE_INFO_REQUESTED, menuType: string }
export const htsInfoActionType = (param: IHtsInfoActionType): IHtsInfoActionType => (
  { type: param.type, menuType: param.menuType }
)

interface IHtsInfoSuccessActionType { type: typeof HTS_TRADE_INFO_SUCCESS, htsData: Array<Object> }
export const htsInfoSuccessActionType = (param: IHtsInfoSuccessActionType): IHtsInfoSuccessActionType => (
  { type: param.type, htsData: param.htsData }
)

interface IHtsInfoFailedActionType { type: typeof HTS_TRADE_INFO_FAILED, msg: string }
export const htsInfoFailedActionType = (param: IHtsInfoFailedActionType): IHtsInfoFailedActionType => (
  { type: param.type, msg: param.msg }
)

export type homeTypes = IMenuModeActionType | IHtsInfoActionType | IHtsInfoSuccessActionType | IHtsInfoFailedActionType
