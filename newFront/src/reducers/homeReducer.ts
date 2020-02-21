import produce from 'immer'
import {
  MENU_MODE_CHANGE_REQUESTED
} from '../actionCmds/homeActionCmd'

const initMap = {
  menuMode: 'android'
}

const homeReducer = (state = initMap, action: any) => {
  let nextState = state

  switch (action.type) {
    case MENU_MODE_CHANGE_REQUESTED:
      nextState = produce(state, (draft) => {
        draft.menuMode = action.menuMode
      })
      break
    default:
      break
  }
  return nextState
}

export default homeReducer
