import {
  HOME_MENU_CHANGE_REQUESTED
} from '../actionCmds/homeActionCmd'

interface IHomeMenuActionType { type: typeof HOME_MENU_CHANGE_REQUESTED, menuMode: string }
export const homeMenuActionType = (param: IHomeMenuActionType): IHomeMenuActionType => (
  { type: param.type, menuMode: param.menuMode }
)

export type homeTypes = IHomeMenuActionType
