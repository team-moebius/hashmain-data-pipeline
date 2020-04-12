import produce from 'immer'
import { HOME_MENU_CHANGE_REQUESTED } from '../actionCmds/homeActionCmd'
import { homeTypes } from '../actions/homeAction'

const initMap = {
  menuMode: 'hts'
}

const homeReducer = (state = initMap, action: homeTypes) => {
  let nextState = state

  switch (action.type) {
    case HOME_MENU_CHANGE_REQUESTED:
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
