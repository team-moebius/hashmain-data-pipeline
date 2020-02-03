import produce from 'immer'

const initMap = {
  isLogin: false
}

const signReducer = (state = initMap, action: any) => {
  let nextState = state

  switch (action.type) {
    case 'LOGIN_STATE_CHANGE_REQUESTED':
      nextState = produce(state, (draft) => {
        draft.isLogin = action.isLogin
      })
      break
    default:
      break
  }
  return nextState
}

export default signReducer
