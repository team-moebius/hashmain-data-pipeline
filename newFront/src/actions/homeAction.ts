import {
  MENU_MODE_CHANGE_REQUESTED
} from '../actionCmds/homeActionCmd'

interface IMenuModeActionType { type: typeof MENU_MODE_CHANGE_REQUESTED, menuMode: string }
export const menuModeActionType = (param: IMenuModeActionType): IMenuModeActionType => (
  { type: param.type, menuMode: param.menuMode }
)

export type homeTypes = IMenuModeActionType
